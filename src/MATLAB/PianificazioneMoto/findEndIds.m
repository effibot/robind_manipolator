function [shapestruct,ok] = findEndIds(nodeList,shape,G,i)
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
    ok=true;
else
    redAdj = findobj(nodeAdj,'prop','r');
    adjRedNode = arrayfun(@(x)findobj(nodeList,'id',x),[redAdj.adj]);
    greenAdj2 = adjRedNode(~ismember(adjRedNode,findobj(adjRedNode,'prop','r')));
    greenAdj2bc = reshape([greenAdj2.bc]',2,[]);

    if ~isempty(greenAdj2)
        [id,~]=dsearchn(greenAdj2bc',goalObsNode.bc);
        allGreenIds2 = bfsearch(G,greenAdj2(id).id);
        shapestruct = struct("id",{i},"pos",{shape},"endid",{greenAdj2(id).id},"allid",{allGreenIds2});
        ok=true;
    else
        shapestruct = struct("id",{i},"pos",{shape},"endid",{0,0},"allid",{0});
        ok=false;
    end

end
