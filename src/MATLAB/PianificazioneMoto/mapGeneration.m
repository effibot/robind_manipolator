function mapGeneration(obs,dim,src)
robotsize=50;
tic
map = makeMap(obs,dim);
toc
M=map.value;
M(M==0)=0;
M(M==1)=256*256*255+256*255+255;
msg = src.UserData.buildMessage(0,"BW",src.UserData.compressImg(M));
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);

%% QT-Decomp
tic
[M, nodeList] = splitandcolor(map, robotsize,src);
toc

%% Adjiacency Matrix
tic
[A, Acomp, Aint, Amid] = adjmatrix(nodeList);
toc
G=graph(A);
gid = gPlot(nodeList, A, Amid, Aint,M,src);
% gPlot(nodeList, A, Amid, Aint,M);
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
msg = src.UserData.buildMessage(0,"ID",gid);
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg = src.UserData.buildMessage(msg,"SHAPE",shapepos);
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg = src.UserData.buildMessage(0,"FINISH",1);
src.UserData.sendMessage(src,msg);

save mapg.mat M obs dim robotsize A Aint Amid G nodeList shapepos Acomp
end