function node = makeMap(obstacleList, dim)
    % return RGB Image as m x m x 3
    %     img = ones([dim,3]);
    img = ones(dim);
    for o = obstacleList'
    img(o(1)-fix(o(3)/2)+1:o(1)-fix(o(3)/2)+o(3),...
        o(2)-fix(o(3)/2)+1:o(2)-fix(o(3)/2)+o(3),:) = 0;
    end
    bc = dim/2;
    lc = [1 1];
    corner = [[1;1],[1;dim(1)],[dim(1);1],[dim(1);dim(1)]];
    id = 1;
    value = img;
    father = 'root';   
    node = rectNode(bc, lc, dim(1), corner, id, value);
    node.setParent(father)
end