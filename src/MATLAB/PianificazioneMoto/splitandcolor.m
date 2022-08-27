function [M, nodeList,imageList] = splitandcolor(map, robotsize, toSave)
    % graphically split the map and return the list of all nodes also.
    decomp(map, robotsize, 0.9);
    dim = size(map.value);
    currdim = dim(1)/2;
    M = 255 * repmat(uint8(map.value), 1, 1, 3);
    Mtemp = map.value;
    nodeList = getAllNode(map, []);
    id = 0;
    images = uint8.empty;
    saving=@(gcf)frame2im(getframe(gcf));

    figure('visible','off')
    imageList = zeros(numel(nodeList), dim(1), dim(2), 3);
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
                    color = [255, 160, 0];
                case 'g'
                    color = [0,255,0];
                case 'r'
                    color = [255,0,0];
            end
            M(corner(1,1):corner(1,2),corner(2,1):corner(2,3)) = mapList(i).value;
           
            M(corner(1,1):corner(1,2),corner(2,1):corner(2,3),1)=color(1);
            %         Mtemp(corner(1,1):corner(1,2),corner(2,1):corner(2,3)) = 0;
           
            M(corner(1,1):corner(1,2),corner(2,1):corner(2,3),2)=color(2);
            %         Mtemp(corner(1,1):corner(1,2),corner(2,1):corner(2,3))=0;
           
            M(corner(1,1):corner(1,2),corner(2,1):corner(2,3),3)=color(3);
            %         Mtemp(corner(1,1):corner(1,2),corner(2,1):corner(2,3))=0;
            
            M(corner(1,1):corner(1,2),corner(2,1),:)=0;
            Mtemp(corner(1,1):corner(1,2),corner(2,1))=0;
            M(corner(1,1),corner(2,1):corner(2,3),:)=0;
            Mtemp(corner(1,1),corner(2,1):corner(2,3))=0;
            %
            M(corner(1,3):corner(1,4),corner(2,3),:)=0;
            Mtemp(corner(1,3):corner(1,4),corner(2,3))=0;
            M(corner(1,2),corner(2,2):corner(2,4),:)=0;
            Mtemp(corner(1,2),corner(2,2):corner(2,4))=0;

            if corner(2,3)==dim(1)
                M(corner(1,1):corner(1,2),corner(2,3),:)=0;
                Mtemp(corner(1,1):corner(1,2),corner(2,3))=0;
            end
            if corner(1,2) == dim(1)
                M(corner(1,2),corner(2,1):corner(2,3),:)=0;
                Mtemp(corner(1,2),corner(2,1):corner(2,3))=0;
            end
            if toSave
%                 imwrite(M,filename,'jpg');
%                 showimage(M);
%                 fm = saving(gcf);
% 
%                 images(end+1,1:size(fm,1),1:size(fm,2),1:size(fm,3))=fm;
%                 saveimage(gcf,'.\mapgenerationimg\constructing\',filename)
            imageList(k,:,:,:) = M;
                k = k+1;
            end
        end
        currdim = currdim/2;
    end
end