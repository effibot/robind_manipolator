function [gids]=gPlot(nodeList, A, Amid, Aint,mapImg,src)
nodeList = nodeList(~ismember(nodeList,findobj(nodeList,'prop','y')));
figure('Visible','off')
gids = [findobj(nodeList,'prop','g').id];
saving=@(gcf)frame2im(getframe(gcf));
showimage(mapImg);
hold on
for i=1:size(A,1)
    % Disegno l'id del nodo
    nodeA = findobj(nodeList,'id',i);
    if ~isempty(nodeA) && nodeA.prop=='g'
        text(nodeA.bc(2),nodeA.bc(1),int2str(nodeA.id),...
            'HorizontalAlignment','center');
    end
    for j=1:size(A,2)
        if A(i,j)==1
            nodeB = findobj(nodeList,'id',j);
            % Disegno l'arco tra due nodi adiacenti
            nodeA_bc = nodeA.bc;
            nodeB_bc = nodeB.bc;
            plot([nodeA_bc(2) nodeB_bc(2)],[nodeA_bc(1),nodeB_bc(1)],...
                'w','LineWidth',0.8);
            % Disegno il punto di intersezione
            plot(Aint(i,j,2), Aint(i,j,1),'bo','MarkerSize',10);
            % Disegno punto intermedio del bordo cella
            plot(Amid(i,j,2), Amid(i,j,1),'r*','MarkerSize',10)
        end
    end
end
msg = src.UserData.buildMessage(0,"GRAPH",src.UserData.compressImg(saving(gcf)));
msg = src.UserData.buildMessage(msg,"FINISH",0);

src.UserData.sendMessage(src,msg);
end