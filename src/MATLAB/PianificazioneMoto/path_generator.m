function [p,dp,ddp,images,error]=path_generator(startId,shape,method,src)
load mapg.mat
redObsbc = reshape([findobj(nodeList, 'prop', 'r').bc]',2,[]);
[id,~]=dsearchn(redObsbc',shape);
goalObs = redObsbc(:,id(1));
goalid = findobj(nodeList,'bc',goalObs');
endId = findAdjNode(goalid,nodeList,Acomp);
if isempty(endId) || endId == 0
    error = 1;
    return;
else
    error = 0;
end
P = shortestpath(G, startId, endId(1));
rbclist = getbcprop(nodeList, 'r');
[p,dp,ddp] = pathfind(nodeList, P, Aint, Amid, rbclist,method);
msg =src.UserData.buildMessage(0,"Q",p);
msg =src.UserData.buildMessage(msg,"dQ",dp);
msg =src.UserData.buildMessage(msg,"ddQ",ddp);
msg =src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
runonmap(M,p,rbclist,nodeList,robotsize,src);
msg =src.UserData.buildMessage(0,"FINISH",1);
src.UserData.sendMessage(src,msg);
save path.mat p dp ddp M rbclist nodeList robotsize
end

