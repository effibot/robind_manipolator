clc
clear
close all
%%
import java.util.*
import java.awt.image.BufferedImage
import java.awt.image.*
addpath(genpath('./'))
server = tcpserver("127.0.0.1",3030);
server.UserData=0;
server.Timeout=10000;
configureCallback(server,"byte",1,@readData)

%%
function readData(src,~)
src.UserData = src.UserData + 1;
disp("Callback Call Count: " + num2str(src.UserData))
% data = read(src,src.NumDatagramsAvailable,"uint8");
if(src.NumBytesAvailable~=0)
    disp("Message Received");
    data = read(src,src.NumBytesAvailable,"uint8");
    hashmap = deserialize(data);
    procedure = hashmap.get("PROC");
    switch(procedure)
        case "MAP"
            obs = hashmap.get("OBSLIST");
            dim = hashmap.get("DIM");
            tic
            [gid,shapepos,mapimg,mapanimation,mapgraph]=mapGeneration(obs,dim');
            toc
            msg = buildMessage(0,"I",gid);
            msg = buildMessage(msg,"S",shapepos);
            msg = buildMessage(msg,"FINISH",0);

            sendMessage(src,msg);
            flush(src);

            tic
            msg = buildMessage(0,"BW",createImage(squeeze(mapimg)));
            msg = buildMessage(msg,"FINISH",0);

            sendMessage(src,msg);
            flush(src);

            toc
            sz = size(mapanimation);
            num = sz(1);
            %             row = sz(2);
            %             col =sz(3);
            %             mapcol = @(i,j,k) reshape(mapanimation(i,j,k,:),1,[]);
            tic
            for i = 1: num
                tic
                msg = buildMessage(0,"ANIMATION"+i,createImage(squeeze(mapanimation(i,:,:,:))));
                msg = buildMessage(msg,"FINISH",0);

                sendMessage(src,msg);
                flush(src);
                toc
            end
            toc
            msg = buildMessage(0,"GRAPH",createImage(squeeze(mapgraph)));
            msg = buildMessage(msg,"FINISH",0);

            sendMessage(src,msg);
            flush(src);

            msg = buildMessage(0,"FINISH",1);
            sendMessage(src,msg);
            flush(src);


    end
end
end
function sendMessage(src,msg)
toSend = serialize(msg);
write(src,toSend,"int8");

end
function msg = buildMessage(msg,key,val)
if msg == 0
    msg = javaObject('java.util.HashMap');
end
msg.put(key,val);
end

function r = deserialize(data)

input = java.io.ByteArrayInputStream(data);
ois = java.io.ObjectInputStream(input);
r =(ois.readObject());

end

function p = serialize(data)
byteOutputStream = java.io.ByteArrayOutputStream();
dataOutputStream = java.io.ObjectOutputStream(byteOutputStream);
dataOutputStream.writeObject(data);
dataOutputStream.flush();
p = byteOutputStream.toByteArray();
byteOutputStream.close();
end

function r = createImage(byteImg)
dim = size(byteImg);
row = dim(1);
col = dim(2);
r =zeros(row,col);
for x = 1:row
    for y = 1:col
        color =  getIntFromColor(byteImg(x,y,1),byteImg(x,y,2),byteImg(x,y,3));
        r(x,y) = color;
    end
end
end
function col = getIntFromColor(R,G,B)
col = (R*65536)+(G*256)+B ;
end