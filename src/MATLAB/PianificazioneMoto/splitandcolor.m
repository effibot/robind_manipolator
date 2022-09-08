function [M, nodeList] = splitandcolor(map, robotsize,src)
% graphically split the map and return the list of all nodes also.
decomp(map, robotsize, 0.9);
dim = size(map.value);
currdim = dim(1)/2;
M = 255*repmat(uint8(map.value),1,1,4);
nodeList = getAllNode(map, []);
id = 0;


figure('visible','off')
% imageList = zeros(numel(nodeList), dim(1), dim(2), 3);
color=zeros(1,1,4,'uint8');

while(currdim >= robotsize)
    mapList = findobj(nodeList, 'dim', currdim);
    for i = 1:size(mapList,1)

        % TODO: consider to set id only to g and r nodes
        mapList(i).id = id+1;
        id = id+1;
        corner = mapList(i).corner;

        switch mapList(i).prop
            case 'y'
                color(1,1,:) = 255*uint8([0,1,1,1]);
            case 'g'
                color(1,1,:) = 255*uint8([0,1,0,1]);
            case 'r'
                color(1,1,:) = 255*uint8([0,0,1,1]);

        end
        
        M(corner(1,1):corner(1,2),corner(2,1):corner(2,3),:)=repmat(color,currdim,currdim);

        M(corner(1,1):corner(1,2),corner(2,1),:)=255*repmat(uint8([0,0,0,1]),currdim,1);
        M(corner(1,1),corner(2,1):corner(2,3),:)=255*repmat(uint8([0,0,0,1]),currdim,1);

        M(corner(1,3):corner(1,4),corner(2,3),:)=255*repmat(uint8([0,0,0,1]),currdim,1);
        M(corner(1,2),corner(2,2):corner(2,4),:)=255*repmat(uint8([0,0,0,1]),currdim,1);

        msg = src.UserData.buildMessage(0,"ANIMATION",...
            src.UserData.compressImg(M));
        msg = src.UserData.buildMessage(msg,"FINISH",0);
        src.UserData.sendMessage(src,msg);
    end
    currdim = currdim/2;
end
msg = src.UserData.buildMessage(0,"ANIMATION",...
    src.UserData.compressImg(M));
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
end