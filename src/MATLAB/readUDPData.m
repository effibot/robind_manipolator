function readUDPData(src,~)
addpath(genpath('.\'))
IPaddr="127.0.0.1";
Port_TX=12345;
data = read(src,src.NumDatagramsAvailable,"string");
if size(data,2)>=1
    values =str2num(data(end).Data);
    data=udpport.datagram.Datagram.empty(0,1);
    %Ricezione dei dati ora è possibile manipolarli
    switch(values(1))
        %Formato datagramma: [Proc,const,q1,q2...]
        case 1 % Inverse Kinematics 
            [qik]=IK_Newton(xdes,ydes,zdes,roll,pitch,yaw)
        case 2 %Visione

            %Specific format:[Proc,obstacleTarget]
            obsTarg=values(4);
            visione(obsTarg);
%             write(src,"["+strjoin(""+strjoin(compose("%d",double(pointToReach)),", ") ,", ")+"]","string",IPaddr,Port_TX);
        case 3 % MapGeneration
            [gid,shapepos]=mapGeneration(obs,dim,robotsize)
        case 4 % PathGenerator
            [p,dp,ddp]=path_generator(startId,shapepos,method)
        case 5 % RunSimulation
            [qr,dqr,ddqr,e]=runsimulation(M,alpha)
        otherwise
            disp("Not mapped case")
    end
end

end


