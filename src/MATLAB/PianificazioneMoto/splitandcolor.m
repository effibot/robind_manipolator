function [M, nodeList] = splitandcolor(map, robotsize,src)
% graphically split the map and return the list of all nodes also.
decomp(map, robotsize, 0.9);
dim = size(map.value);
currdim = dim(1)/2;
M = 255 * repmat(uint8(map.value), 1, 1, 3);
nodeList = getAllNode(map, []);
id = 0;


figure('visible','off')
% imageList = zeros(numel(nodeList), dim(1), dim(2), 3);
k = 1;
while(currdim >= robotsize)
    mapList = findobj(nodeList, 'dim', currdim);
    for i = 1:size(mapList,1)
        % TODO: consider to set id only to g and r nodes
        mapList(i).id = id+1;
        id = id+1;
        corner = mapList(i).corner;
        color=[];
        switch mapList(i).prop
            case 'y'
                color = [1, 1, 0];
            case 'g'
                color = [0,1,0];
            case 'r'
                color = [1,0,0];
        end
        M(corner(1,1):corner(1,2),corner(2,1):corner(2,3)) = mapList(i).value;

        M(corner(1,1):corner(1,2),corner(2,1):corner(2,3),1)=color(1);
        %         Mtemp(corner(1,1):corner(1,2),corner(2,1):corner(2,3)) = 0;

        M(corner(1,1):corner(1,2),corner(2,1):corner(2,3),2)=color(2);
        %         Mtemp(corner(1,1):corner(1,2),corner(2,1):corner(2,3))=0;

        M(corner(1,1):corner(1,2),corner(2,1):corner(2,3),3)=color(3);
        %         Mtemp(corner(1,1):corner(1,2),corner(2,1):corner(2,3))=0;
        M(corner(1,1):corner(1,2),corner(2,1),:)=0;
        %             Mtemp(corner(1,1):corner(1,2),corner(2,1))=0;
        M(corner(1,1),corner(2,1):corner(2,3),:)=0;
        %             Mtemp(corner(1,1),corner(2,1):corner(2,3))=0;

        M(corner(1,3):corner(1,4),corner(2,3),:)=0;
        %             Mtemp(corner(1,3):corner(1,4),corner(2,3))=0;
        M(corner(1,2),corner(2,2):corner(2,4),:)=0;
        %             Mtemp(corner(1,2),corner(2,2):corner(2,4))=0;

        %             if corner(2,3)==dim(1)
        %                 M(corner(1,1):corner(1,2),corner(2,3),:)=0;
        % %                 Mtemp(corner(1,1):corner(1,2),corner(2,3))=0;
        %             end
        %             if corner(1,2) == dim(1)
        %                 M(corner(1,2),corner(2,1):corner(2,3),:)=0;
        % %                 Mtemp(corner(1,2),corner(2,1):corner(2,3))=0;
        %             end
        msg = src.UserData.buildMessage(0,"ANIMATION"+k,...
            src.UserData.compressImg(M));
        msg = src.UserData.buildMessage(msg,"FINISH",0);
        src.UserData.sendMessage(src,msg);
        %                 imageList(k,:,:,:) = M;
        k = k+1;
    end
    currdim = currdim/2;
end
end