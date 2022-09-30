function [p,dp,ddp,images,error]=path_generator(startId,shape,method,src)
load mapg.mat
redListNode = findobj(nodeList, 'prop', 'r');
redObsbc = reshape([redListNode.bc]',2,[]);
shapeposlist = reshape([list.pos]',2,[])';
startNode = findobj(nodeList,'id',startId);
idx = find(ismember(shapeposlist,shape,'rows'));
endId = list(idx).endid;
startPos = [findobj(nodeList,'id',endId).bc,0];
pend=shapepos(idx,2:4);
pobs = pend;
P = shortestpath(G, startId, endId);
% if P == startId 
%     msg = src.UserData.buildMessage(0,"FINISH",1);
%     src.UserData.sendMessage(src,msg);
%     return
% elseif isempty(P)
%     msg = src.UserData.buildMessage(0,"ERROR_CYCLE",3);
%     msg = src.UserData.buildMessage(msg,"FINISH",1);
%     src.UserData.sendMessage(src,msg);
%     return
% end
if(length(P)==1 && findobj(nodeList,'id',endId).dim == 64)
msg = src.UserData.buildMessage(0, "PATHIDS",[P,P]);
p = fliplr([startPos(1:2);startPos(1:2)])';
dp = zeros(2,2);
ddp = zeros(2,2);
pik = [(pend(1:2)+p(:,1)')./2,pend(3)] ;

else
msg = src.UserData.buildMessage(0, "PATHIDS",[P,P]);
[p,dp,ddp,pik] = pathfind(nodeList, P, Aint, Amid, redObsbc',method,pend);
pend = pik;
end
startPos = [p(1:2,end);0]';
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);

msg =src.UserData.buildMessage(0,"Q",p');
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
pobs
msg =src.UserData.buildMessage(0,"SHAPEBC",shape);
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg =src.UserData.buildMessage(0,"PIK",[idx-1,pik]);
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg =src.UserData.buildMessage(0,"dQ",dp');
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg =src.UserData.buildMessage(0,"ddQ",ddp');
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
runonmap(M,p,redObsbc',nodeList,robotsize,src);
msg =src.UserData.buildMessage(0,"FINISH",1);
src.UserData.sendMessage(src,msg);
simTime = ceil(length(p)/100);
save path.mat p dp ddp M redObsbc nodeList robotsize pend startPos simTime
end

