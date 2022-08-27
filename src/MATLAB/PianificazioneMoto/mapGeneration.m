function [gid,shapepos,mapimg,mapworking,mapgraph]=mapGeneration(obs,dim)
mapimg = uint8.empty;
robotsize=50;
map = makeMap(obs,dim);
showimage(map.value);
% saveimage(gcf,'.\mapgenerationimg\generated\','bw.png');
saving=@(gcf)frame2im(getframe(gcf));
fm = saving(gcf);
mapimg(end+1,1:size(fm,1),1:size(fm,2),1:size(fm,3))=fm;
toSave = true;
toShow = false;
%% QT-Decomp
[M, nodeList,mapworking] = splitandcolor(map, robotsize, toSave);
% mapImg = imshow(M);
colormat = mapworking(80,:,:,:);
save colormat.mat  colormat

%% Adjiacency Matrix
[A, Acomp, Aint, Amid] = adjmatrix(nodeList);
G=graph(A);
[gid,mapgraph] = gPlot(nodeList, A, Amid, Aint,M);
nobs = size(obs,1);
shapepos = zeros(3,3);
for i = 1:3
    form = i-1;
    obb = randi(nobs,1);
    pos = obs(obb,1:2);
        while ~isempty(find(ismember(shapepos(:,2:3),pos,'rows'), 1))
            obb = randi([1 nobs],1);
            pos = obs(obb,1:2);
        
        end
    shapepos(i,:) = [form,pos];
end
save mapg.mat M obs dim robotsize A Aint Amid G nodeList shapepos Acomp
end