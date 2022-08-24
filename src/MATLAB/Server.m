clc
clear
close all
import java.util.*
addpath(genpath('./'))
server = tcpserver("127.0.0.1",3030);
server.UserData=0;
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
                [gid,shapepos,mapimg,mapanimation,mapgraph]=mapGeneration(obs,dim');
                msg = javaObject('java.util.HashMap');
                msg.put("GREENID",gid);
                msg.put("SHAPEPOS",shapepos);
%                 toSend = serialize(msg);
%                 write(src,toSend,"uint8");
                msg = javaObject('java.util.HashMap');
                msg.put("MAPIMG",mapimg);
                msg.put("MAPANIMATION",mapanimation);
                msg.put("MAPGRAPH",mapgraph);
                toSend = serialize(msg);
                write(src,toSend,"uint8");
        end
    end
%     tosend = magic(10);
%     row = size(tosend,1);
%     for i = 1:row
%     %     serializeData =serialize(tosend(i,:));
%     %     write(src,typecast(serialize(tosend(i,:)),"uint8"),"uint8");
%     msg = serialize(tosend(i,:));
%         write(src,msg,"int8");
%     
%     
%     end
%     msg = serialize(0);
%     write(src,msg,"int8");
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
p = byteOutputStream.toByteArray();
end