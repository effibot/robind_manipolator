clc
clear server
clear 
close all
%%
import java.util.*
import java.awt.image.BufferedImage
import java.awt.image.*
addpath(genpath('./'))
server = tcpserver("127.0.0.1",3030,"ConnectionChangedFcn",@connectionFcn);
configureTerminator(server,255);
configureCallback(server,"terminator",@readData);

%%
function readData(src,~)
if(src.NumBytesAvailable~=0)
    disp("Message Received");
    data = read(src,src.NumBytesAvailable,"int8");
    hashmap = deserialize(data);

    procedure = hashmap.get("PROC");
    switch(procedure)
        case "MAP"
            obs = hashmap.get("OBSLIST");
            dim = hashmap.get("DIM");

            [gid,shapepos,mapimg,mapanimation,mapgraph]=mapGeneration(obs,dim');

            msg = buildMessage(0,"I",gid);
            msg = buildMessage(msg,"S",shapepos);
            msg = buildMessage(msg,"FINISH",1);

            sendMessage(src,msg);

          
%             msg = buildMessage(0,"BW",createImage(squeeze(mapimg)));
%             msg = buildMessage(msg,"FINISH",0);
% 
%             sendMessage(src,msg);
% 
%             sz = size(mapanimation);
%             num = sz(1);
%             for i = 1: num
%                 msg = buildMessage(0,"ANIMATION"+i,createImage(squeeze(mapanimation(i,:,:,:))));
%                 msg = buildMessage(msg,"FINISH",0);
%                 sendMessage(src,msg);
%                 flush(src);
%             end
%             msg = buildMessage(0,"GRAPH",createImage(squeeze(mapgraph)));
%             msg = buildMessage(msg,"FINISH",0);
%             sendMessage(src,msg);

           

        case "PATH"
            start = hashmap.get("START");
            endp = hashmap.get("END");
            method = hashmap.get("METHOD");
            [p,dp,ddp,images,error]=path_generator(start,endp',method);
            if error == 0
                msg = buildMessage(0,"Q",p);
                msg = buildMessage(msg,"dQ",dp);
                msg = buildMessage(msg,"ddQ",ddp);
                msg = buildMessage(msg,"FINISH",1);
                sendMessage(src,msg);
             
            else
                msg = buildMessage(0,"ERROR",1);
                msg = buildMessage(msg,"FINISH",1);
                sendMessage(src,msg);
            end

        case "SYM"
            mass = hashmap.get("M");
            alpha = hashmap.get("ALPHA");
            [qr,dqr,ddqr,e]=runsimulation(mass,alpha);
            msg = buildMessage(0,"Q",qr);
            msg = buildMessage(msg,"dQ",dqr);
            msg = buildMessage(msg,"ddQ",ddqr);
            msg = buildMessage(msg,"E",e);
            msg = buildMessage(msg,"FINISH",1);
            sendMessage(src,msg);
        case "IK"
            xdes = hashmap.get("X");
            ydes = hashmap.get("Y");
            zdes = hashmap.get("Z");
            roll = hashmap.get("ROLL");
            pitch = hashmap.get("PITCH");
            yaw = hashmap.get("YAW");
            [qik]=newtongrad(xdes,ydes,zdes,roll,pitch,yaw);
            msg = buildMessage(0,"Q",qik);
            msg = buildMessage(msg,"FINISH",1);
            sendMessage(src,msg);
        case "VIS"
            shape = hashmap.get("SHAPE");
            [objArea,objPerim,objShape,ang,frame]=visione(shape);
            msg = buildMessage(0,"AREA",objArea);
            msg = buildMessage(msg,"PERIM",objPerim);
            msg = buildMessage(msg,"FORMA",objShape);
            msg = buildMessage(msg,"ORIENT",ang);
%             msg = buildMessage(msg,"IMG",frame);
            msg = buildMessage(msg,"FINISH",1);
            sendMessage(src,msg);
    end
end
            flush(src);

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
byteOutputStream.flush();
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