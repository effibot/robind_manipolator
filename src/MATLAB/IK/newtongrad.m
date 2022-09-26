function newtongrad(roll,pitch,yaw,src)
    load path.mat pend startPos
    pfinal =pend-startPos;% [50 50 50];
    P=[pfinal(1);pfinal(2);pfinal(3)+20;deg2rad(roll);deg2rad(pitch);deg2rad(yaw)];
    q=sym('q',[6,1]);
    L=[33;50;0;51;0;12];
    dof=6;
    col=4;
    dhparams=sym('b',[dof,col]);
    dhparams(1,1:end)=[q(1,1),L(1,1),-pi/2,0];
    dhparams(2,1:end)=[q(2,1),0,0,L(2,1)];
    dhparams(3,1:end)= [q(3,1),0,+pi/2,L(3,1)];
    dhparams(4,1:end)= [q(4,1),L(4,1),-pi/2,0];
    dhparams(5,1:end)= [q(5,1),0,pi/2,0];
    dhparams(6,1:end)=[q(6,1),L(6,1),0,0];
    dr=DH(dhparams(1:6,:));
    Rq = dr(1:3,1:3);
    q0=[0;-pi/2; pi;0.1;0.1;0.1];
    %% Inverse Functions
%     de = DH(dhparams);
    dep = dr(1:3,4);
%     Rzyx Roll 180,Yaw 60 Pitch 4
    pitchp = atan2(-Rq(3,1),sqrt(1-Rq(3,1)^2));
    rollp=atan2(Rq(2,1)/sqrt(1-Rq(3,1)^2),Rq(1,1)/sqrt(1-Rq(3,1)^2));
    yawp = atan2(Rq(3,2)/sqrt(1-Rq(3,1)^2),Rq(3,3)/sqrt(1-Rq(3,1)^2));
%     pitchp = atan2(sqrt(1-Rq(3,3)^2),Rq(3,3));
%     yawp=atan2(Rq(3,2)/sqrt(1-Rq(3,3)^2),Rq(3,1)/(-sqrt(1-Rq(3,3)^2)));
%     rollp = atan2(Rq(2,3)/sqrt(1-Rq(3,3)^2),Rq(1,3)/sqrt(1-Rq(3,3)^2));

    j = jacobian([dep;rollp;pitchp;yawp],q);
    J= matlabFunction(j);
    err = [dep;rollp;pitchp;yawp]-P;
    E = matlabFunction(err);
    Rv= matlabFunction(Rq);
    %% Newton-Gradient Hybrid
    cond= @(L1,L2,L4,P1,P2,P3)(-L2.^2-L4.^2+P1.^2+P2.^2+(L1-P3).^2)./(L2.*L4.*2.0);
    if cond(L(1),L(2),L(4),pfinal(1),pfinal(2),pfinal(3))<=1 &&...
            cond(L(1),L(2),L(4),pfinal(1),pfinal(2),pfinal(3))>=-1
        while 1
            % Jacobian
            Jval =J(q0(1,1),q0(2,1),q0(3,1),q0(4,1),q0(5,1),q0(6,1));
            %Newton
            qdot = -pinv(Jval)*E(q0(1,1),q0(2,1),q0(3,1),q0(4,1),q0(5,1),q0(6,1));
            qN = q0+0.009*qdot;
            %Gradiente
%             qdot = -Jval'*(E(q0(1,1),q0(2,1),q0(3,1),q0(4,1),q0(5,1),q0(6,1)));
%             qG = q0+1e-6*qdot;
            errorNewton = norm(E(qN(1,1),qN(2,1),qN(3,1),qN(4,1),qN(5,1),qN(6,1)));
%             errorGradient = norm(E(qG(1,1),qG(2,1),qG(3,1),qG(4,1),qG(5,1),qG(6,1)));
            q0=qN;
            det(Jval);
            Rv(q0(1,1),q0(2,1),q0(3,1),q0(4,1),q0(5,1),q0(6,1))
            errnorm = errorNewton
%             if abs(det(Jval)) <1e-1 
%                 q0=qG;
%                 errnorm = errorGradient;
%             end
            % Stop Criteria
            if errnorm<1e-2
                q0
                msg = src.UserData.buildMessage(0,"Q",q0);
                msg = src.UserData.buildMessage(msg,"FINISH",0);
                src.UserData.sendMessage(src,msg);
                break;
            end
            msg = src.UserData.buildMessage(0,"Q",q0);
            msg = src.UserData.buildMessage(msg,"FINISH",0);
            src.UserData.sendMessage(src,msg);
            msg = src.UserData.buildMessage(0,"ENEWTON",errnorm);
            msg = src.UserData.buildMessage(msg,"FINISH",0);
            src.UserData.sendMessage(src,msg);
        end
    end
    msg = src.UserData.buildMessage(0,"FINISH",1);
    src.UserData.sendMessage(src,msg);
end


