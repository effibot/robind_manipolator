function mapGeneration(obs,dim,src)
robotsize=50;
map = makeMap(obs,dim);
MRGB = 255*repmat(uint8(map.value),1,1,3);
MRGB = cat(3,MRGB,ones(1024,'uint8')*255);
msg = src.UserData.buildMessage(0,"BW",src.UserData.compressImg(MRGB));
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);

%% QT-Decomp
[M, nodeList] = splitandcolor(map, robotsize,src);

%% Adjiacency Matrix
[A, Acomp, Aint, Amid] = adjmatrix(nodeList);
G=graph(A);
[gid,M] = gPlot(nodeList, A, Amid, Aint,M,src,map.value);

shapepos = zeros(3,3);
color = zeros(3,1,3,'uint8');
color(1,1,:)=[246,182,41];  %Sfera: arancione
color(2,1,:)= [205,117,149]; %Cono: viola
color(3,1,:) = [88, 238, 255]; %Cubo: celeste
msg = src.UserData.buildMessage(0,"SHAPEIDS",0);
obstemp = obs;
for i = 1:3
    form = i-1;

    while  ~isempty(obstemp)
         nobs = size(obstemp,1);

        obb = randi(nobs,1);
        pos = obstemp(obb,1:2);
        if isempty(find(ismember(shapepos(:,2:3),pos,'rows'), 1))            
            [listElem,boolean]= findEndIds(nodeList,pos,G,i);
            if boolean
                radius = obs(ismember(obs(:,1:2),pos,'rows'),3);
                M(pos(1)-fix(radius/2)+1:pos(1)+fix(radius/2),...
                    pos(2)-fix(radius/2)+1:pos(2)+fix(radius/2),:)=...
                    repmat(color(i,1,:),radius,radius,1);
                break
            end
        end
        obstemp(obb,:)=[];

    end

    shapepos(i,:) = [form,pos];
    list(i) = listElem;

    switch(i)
        case 1
            msg = src.UserData.buildMessage(msg,"SFERA",list(i).allid);
        case 2
            msg = src.UserData.buildMessage(msg,"CONO",list(i).allid);
        case 3
            msg = src.UserData.buildMessage(msg,"CUBO",list(i).allid);
    end
end
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);

M = cat(3,M,ones(1024,'uint8')*255);
msg = src.UserData.buildMessage(0,"ANIMATION",src.UserData.compressImg(M));
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
M= M(:,:,1:3);

msg = src.UserData.buildMessage(0,"ID",gid);
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg = src.UserData.buildMessage(msg,"SHAPE",shapepos);
msg = src.UserData.buildMessage(msg,"FINISH",0);
src.UserData.sendMessage(src,msg);
msg = src.UserData.buildMessage(0,"FINISH",1);
src.UserData.sendMessage(src,msg);

save mapg.mat M obs dim robotsize A Aint Amid G nodeList shapepos Acomp list
end