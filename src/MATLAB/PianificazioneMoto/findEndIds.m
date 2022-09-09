function shapestruct = findEndIds(nodeList,shape,G,i)
redListNode = findobj(nodeList, 'prop', 'r');
redObsbc = reshape([redListNode.bc]',2,[]);
[id,~]=dsearchn(redObsbc',shape);
goalObsNode = redListNode(id(1));
nodeAdj = arrayfun(@(x)findobj(nodeList,'id',x),goalObsNode.adj);
greenAdj = nodeAdj(~ismember(nodeAdj,findobj(nodeAdj,'prop','r')));
greenAdjbc = reshape([greenAdj.bc]',2,[]);
if ~isempty(greenAdj)
[id,~]=dsearchn(greenAdjbc',goalObsNode.bc);
allGreenIds = bfsearch(G,greenAdj(id).id);
shapestruct = struct("id",{i},"pos",{shape},"endid",{greenAdj(id).id},"allid",{allGreenIds});
else
    shapestruct = struct("id",{i},"pos",{shape},"endid",{0},"allid",{0});
end
end