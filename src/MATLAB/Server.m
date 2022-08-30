clc
clear server
clear 
close all
addpath(genpath('./'))

%%
compressor = serverUtils;
server = tcpserver("127.0.0.1",3030,"ConnectionChangedFcn",@connectionFcn);
server.UserData=compressor;
configureTerminator(server,255);
configureCallback(server,"terminator",@readData);

%%
function readData(src,~)
if(src.NumBytesAvailable~=0)
    disp("Message Received");
    data = read(src,src.NumBytesAvailable,"int8");
    hashmap = src.UserData.deserialize(data);

    procedure = hashmap.get("PROC");
    switch(procedure)
        case "MAP"
            obs = hashmap.get("OBSLIST");
            dim = hashmap.get("DIM");
            tic
            mapGeneration(obs,dim',src);
            toc
          
        case "PATH"
            start = hashmap.get("START");
            endp = hashmap.get("END");
            method = hashmap.get("METHOD");
            [p,dp,ddp,images,error]=path_generator(start,endp(1:2)',method,src);


        case "SYM"
            mass = hashmap.get("M");
            alpha = hashmap.get("ALPHA");
            runsimulation(mass,alpha,src);
        case "IK"
            xdes = hashmap.get("X");
            ydes = hashmap.get("Y");
            zdes = hashmap.get("Z");
            roll = hashmap.get("ROLL");
            pitch = hashmap.get("PITCH");
            yaw = hashmap.get("YAW");
            newtongrad(xdes,ydes,zdes,roll,pitch,yaw,src);

        case "VIS"
            shape = hashmap.get("SHAPE");
            visione(shape);
    end
end
        flush(src);

end

