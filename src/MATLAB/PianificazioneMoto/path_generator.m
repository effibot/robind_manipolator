function [p,dp,ddp,images,error]=path_generator(startId,shape,method)
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
images = 0;
% images=runonmap(M,p,rbclist,nodeList,robotsize);
save path.mat p dp ddp M rbclist nodeList robotsize
end

