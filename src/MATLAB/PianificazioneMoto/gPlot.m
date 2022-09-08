function [gids,M]=gPlot(nodeList, A, Amid, Aint,mapImg,src,originalMap)
saving=@(gcf)frame2im(getframe(gcf));
f=figure('Visible','off');
f.Position=[0,0,1024,1024];
f.Units='points';
nodeList = nodeList(~ismember(nodeList,findobj(nodeList,'prop','y')));
gids = [findobj(nodeList,'prop','g').id];
% convert from ARGB_INT to RGB
% [~,red,green,blue] = src.UserData.convertImage(mapImg);
% mapImg = cat(3,red,green,blue);
imshow(mapImg(:,:,1:3),'Border','tight');
hold on
for i=1:size(A,1)
    % Disegno l'id del nodo
    nodeA = findobj(nodeList,'id',i);
    if ~isempty(nodeA) && nodeA.prop=='g'
        text(nodeA.bc(2),nodeA.bc(1),int2str(nodeA.id),...
            'HorizontalAlignment','center');
    end
    for j=1:size(A,2)
        if A(i,j)~=0
            nodeB = findobj(nodeList,'id',j);
            % Disegno l'arco tra due nodi adiacenti
            nodeA_bc = nodeA.bc;
            nodeB_bc = nodeB.bc;
            plot([nodeA_bc(2) nodeB_bc(2)],[nodeA_bc(1),nodeB_bc(1)],...
                'w','LineWidth',0.8);
            hold on
            % Disegno il punto di intersezione
            plot(Aint(i,j,2), Aint(i,j,1),'bo','MarkerSize',10);
            hold on
            % Disegno punto intermedio del bordo cella
            plot(Amid(i,j,2), Amid(i,j,1),'r*','MarkerSize',10)
            hold on
        end
    end
end
im = saving(gcf);

J = imresize(im,[1024,1024],'cubic');
JJ = bsxfun(@times,J,cast(uint8(originalMap),'like',J));
JJBGRA = cat(3,JJ,ones(1024,'uint8')*255);

msg = src.UserData.buildMessage(0,"ANIMATION",src.UserData.compressImg(JJBGRA));
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
M = JJ;
end