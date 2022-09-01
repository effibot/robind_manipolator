function [gids]=gPlot(nodeList, A, Amid, Aint,mapImg,src)
saving=@(gcf)frame2im(getframe(gcf));
f=figure('Visible','off');
f.Position=[0,0,1024,1024];
f.Units='points';
nodeList = nodeList(~ismember(nodeList,findobj(nodeList,'prop','y')));
gids = [findobj(nodeList,'prop','g').id];
imm = zeros(1024,1024,3);
for i = 1:size(mapImg,1)
    for j = 1: size(mapImg,2)
        colorInt = mapImg(i,j);
        curR = floor(colorInt / (256*256));
        curG = floor((colorInt - curR*256*256)/256);
        curB = colorInt - curR*256*256 - curG*256;
        imm(i,j,1)=curR;
        imm(i,j,2)=curG;
        imm(i,j,3)=curB;
    end
end
imshow(imm,'Border','tight');
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
sz = size(J);
intRGBImg = zeros(1024,1024);
for i = 1 : sz(1)
    for j = 1 : sz(2)
        intRGBImg(i,j)=256*256*double(J(i,j,1))+256*double(J(i,j,2))+double(J(i,j,3));
    end
end
msg = src.UserData.buildMessage(0,"GRAPH",src.UserData.compressImg(intRGBImg));
msg = src.UserData.buildMessage(msg,"FINISH",0);

src.UserData.sendMessage(src,msg);
end