function [p,dp,ddp,images,error]=path_generator(startId,shape,method,src)
load mapg.mat
redListNode = findobj(nodeList, 'prop', 'r');
redObsbc = reshape([redListNode.bc]',2,[]);
[id,~]=dsearchn(redObsbc',shape);
goalObsNode = redListNode(id);
nodeAdj = arrayfun(@(x)findobj(nodeList,'id',x),goalObsNode.adj);
greenAdj = nodeAdj(~ismember(nodeAdj,findobj(nodeAdj,'prop','r')));
greenAdjbc = reshape([greenAdj.bc]',2,[]);
[id,~]=dsearchn(greenAdjbc',goalObsNode.bc);
endId = greenAdj(id).id;

P = shortestpath(G, startId, endId);

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
save path.mat p dp ddp M rbclist nodeList robotsize
end

