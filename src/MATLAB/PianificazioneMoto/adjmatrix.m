function [A, Acomp, Aint, Amid] = adjmatrix(nodeList)
    nodeList = nodeList(~ismember(nodeList,findobj(nodeList,'prop','y')));
    A = zeros(size(nodeList,2));
    Acomp = zeros(size(nodeList,2));
    Amid = zeros([size(A),2]);
    Aint = zeros([size(A),2]);
    for node=nodeList
        id = node.id;
        tempList = nodeList(~ismember(nodeList,findobj(nodeList,'id',id)));
        % nodespace = borderLeft, borderTop, borderRight, borderDown
        nodespace = unique(borderSpace(node,1)','rows','stable')';
        %     plot(nodespace(1,:),nodespace(2,:),'g','markersize',5)
        for t = tempList
            tnodespace = unique(borderSpace(t,0)','rows','stable')';
            [in, on] = inpolygon(tnodespace(1,:),tnodespace(2,:),...
                nodespace(1,:),nodespace(2,:));
            temp = find(on);
            if ~isempty(temp)
%                 if toShow
%                     figure
%                     plot(nodespace(1,:),nodespace(2,:))
%                     axis equal
%                     hold on
%                     plot(tnodespace(1,in),...
%                         tnodespace(2,in),'r+') % points inside
%                     plot(tnodespace(1,~in),...
%                         tnodespace(2,~in),'bo') % points outside
%                     hold off
%                 end
                minVert = tnodespace(:,min(temp));
                maxVert = tnodespace(:,max(temp));
                segDim = abs(minVert - maxVert);
                segDim = segDim(segDim ~= 0,:);
                maxSize = min(node.dim,t.dim);
                if maxSize >= segDim
                    node.setAdj(t.id);
                    Acomp(node.id,t.id) = 1;
                    Acomp(t.id,node.id) = 1;
                    if strcmp(node.prop,'g') && strcmp(t.prop,'g')
                        dist = pdist2(node.bc,t.bc);
                        A(node.id,t.id) = dist;
                        A(t.id,node.id) = dist;                        
                        commonborder = tnodespace(:,in);
                        cm=[];
                        if commonborder(1,1)==commonborder(1,2)
                            c2 = nearest(min(commonborder(2,:))):nearest(max(commonborder(2,:)));
                            c1 = ones(1,size(c2,2))*nearest(commonborder(1,1));
                        else
                            c1=nearest(min(commonborder(1,:))):nearest(max(commonborder(1,:)));
                            c2 = ones(1,size(c1,2))*nearest(commonborder(2,1));
                        end
                        cm = [c1;c2];
                        Amid(node.id, t.id, :) = flipud(ceil(cm(:,fix((size(cm,2))/2))));
                        [yi, xi] = findintersection(node,t);
                        Aint(node.id,t.id,:) = [yi, xi];
                    end
                end
            end
        end
    end
%     Aint = fixmeasure(Aint);
%     Amid = fixmeasure(Amid);
end
function Afix = fixmeasure(A)
    Afix = A;
    for i = 1:size(A,1)
        for j = 1:size(A,1)
            if A(i,j,1) ~= A(j,i,1) || A(i,j,2) ~= A(j,i,2)
                y = ceil((A(i,j,1) + A(j,i,1)) /2);
                x = ceil((A(i,j,2) + A(j,i,2)) /2);                              
                Afix(i,j,:) = [y, x];
                Afix(j,i,:) = [y, x];
            end
        end
    end
    
end