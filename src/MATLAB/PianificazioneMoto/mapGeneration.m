function [gid,shapepos]=mapGeneration(obs,dim)
% addpath(genpath('.\')); 
delete('.\mapgenerationimg\generated\*.png');
delete('.\mapgenerationimg\constructing\*.png');
delete('.\mapgenerationimg\generatedsim\*.png');
delete('.\mapgenerationimg\originalsim\*.png');
mkdir mapgenerationimg\generated
mkdir mapgenerationimg\constructing
mkdir mapgenerationimg\generatedsim
mkdir mapgenerationimg\originalsim
robotsize=50;
map = makeMap(obs,dim);
showimage(map.value);
saveimage(gcf,'.\mapgenerationimg\generated\','bw.png');
toSave = true;
toShow = false;
%% QT-Decomp
[M, nodeList] = splitandcolor(map, robotsize, toSave, toShow);
% mapImg = imshow(M);
%% Adjiacency Matrix
[A, Acomp, Aint, Amid] = adjmatrix(nodeList);
G=graph(A);
gid = gPlot(nodeList, A, Amid, Aint,M);
nobs = size(obs,1);
shapepos = zeros(3,3);
for i = 1:3
    form = randi(2,1);
    obb = randi(nobs,1);
    pos = obs(obb,1:2);
    shapepos(i,:) = [form,pos];
end
save mapg.mat M obs dim robotsize A Aint Amid G nodeList shapepos Acomp
end