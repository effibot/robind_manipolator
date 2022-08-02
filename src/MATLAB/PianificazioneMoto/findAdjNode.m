function endid =findAdjNode(obsId,nodeList,A)
    idadj= find(A(obsId.id,:)~=0);
    if ~isempty(idadj)
        for id=idadj
            node = findobj(nodeList,'id',id);
            if strcmp(node.prop,'g')
                endid = id;
                return
            end
        end
    end
end