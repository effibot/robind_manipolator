function [p,dp,ddp]=path_generator(startId,shapepos,method)
load mapg.mat
redObsbc = reshape([findobj(nodeList, 'prop', 'r').bc]',2,[]);
distance=abs(redObsbc-shapepos(1,2:end)');
[~,id]=min(distance,[],2);
goalObs = redObsbc(:,id(1));
goalid = findobj(nodeList,'bc',goalObs');
endId = findAdjNode(goalid,nodeList,Acomp);
P = shortestpath(G, startId, endId);
rbclist = getbcprop(nodeList, 'r');
[p,dp,ddp] = pathfind(nodeList, P, Aint, Amid, rbclist,method);
runonmap(M,p,rbclist,nodeList,robotsize,'originalsim\');
save path.mat p dp ddp M rbclist nodeList robotsize
end

