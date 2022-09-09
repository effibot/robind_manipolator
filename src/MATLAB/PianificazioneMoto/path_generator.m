function [p,dp,ddp,images,error]=path_generator(startId,shape,method,src)
load mapg.mat
redListNode = findobj(nodeList, 'prop', 'r');
redObsbc = reshape([redListNode.bc]',2,[]);
shapeposlist = reshape([list.pos]',2,[])';
idx = find(ismember(shapeposlist,shape,'rows'));
endId = list(idx).endid;
P = shortestpath(G, startId, endId);
if P == startId 
    msg = src.UserData.buildMessage(0,"ERROR_STID",2);
    msg = src.UserData.buildMessage(msg,"FINISH",1);
    src.UserData.sendMessage(src,msg);
    return
elseif isempty(P)
    msg = src.UserData.buildMessage(0,"ERROR_CYCLE",3);
    msg = src.UserData.buildMessage(msg,"FINISH",1);
    src.UserData.sendMessage(src,msg);
    return
end
msg = src.UserData.buildMessage(0, "PATHIDS",P);
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
[p,dp,ddp] = pathfind(nodeList, P, Aint, Amid, redObsbc',method);
msg =src.UserData.buildMessage(0,"Q",p);
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg =src.UserData.buildMessage(0,"dQ",dp);
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg =src.UserData.buildMessage(0,"ddQ",ddp);
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
runonmap(M,p,redObsbc',nodeList,robotsize,src);
msg =src.UserData.buildMessage(0,"FINISH",1);
src.UserData.sendMessage(src,msg);
save path.mat p dp ddp M redObsbc nodeList robotsize
end

