function [qik]=IK_Newton(xdes,ydes,zdes,roll,pitch,yaw)
    qik = double.empty(6,0);
    pend= [xdes;ydes;zdes];
    P=[pend(1);pend(2);pend(3);roll;pitch;yaw];
    q=sym('q',[6,1]);
    L=[40;62;0;60;0;12];
    dof=6;
    col=4;
    dhparams=sym('b',[dof,col]);
    dhparams(1,1:end)=[q(1,1),L(1,1),+pi/2,0];
    dhparams(2,1:end)=[q(2,1),0,0,L(2,1)];
    dhparams(3,1:end)= [q(3,1),0,-pi/2,L(3,1)];
    dhparams(4,1:end)= [q(4,1),L(4,1),pi/2,0];
    dhparams(5,1:end)= [q(5,1),0,-pi/2,0];
    dhparams(6,1:end)=[q(6,1),L(6,1),0,0];
    dr=DH(dhparams(1:6,:));
    Rq = dr(1:3,1:3);
    q0=[0;pi/2;-pi;0;0;0];
    %% Inverse Functions
    de = DH(dhparams);
    dep = de(1:3,4);
    pitchp = atan2(sqrt(1-Rq(3,3)^2),Rq(3,3));
    rollp=atan2(Rq(3,2)/sqrt(1-Rq(3,3)^2),Rq(3,1)/(-sqrt(1-Rq(3,3)^2)));
    yawp = atan2(Rq(2,3)/sqrt(1-Rq(3,3)^2),Rq(1,3)/sqrt(1-Rq(3,3)^2));
    j = jacobian([dep;rollp;pitchp;yawp],q);
    J= matlabFunction(j);
    err = [dep;rollp;pitchp;yawp]-P;
    E = matlabFunction(err);
    %% Newton-Gradient Hybrid
    cond= @(L1,L2,L4,P1,P2,P3)(-L2.^2-L4.^2+P1.^2+P2.^2+(L1-P3).^2)./(L2.*L4.*2.0);
    if cond(L(1),L(2),L(4),pend(1),pend(2),pend(3))<=1 &&...
            cond(L(1),L(2),L(4),pend(1),pend(2),pend(3))>=-1
        while 1
            % Jacobian
            Jval =J(q0(1,1),q0(2,1),q0(3,1),q0(4,1),q0(5,1),q0(6,1));
            %Newton
            qdot = -pinv(Jval)*E(q0(1,1),q0(2,1),q0(3,1),q0(4,1),q0(5,1),q0(6,1));
            qN = q0+0.09*qdot;
            %Gradiente
            qdot = -Jval'*(E(q0(1,1),q0(2,1),q0(3,1),q0(4,1),q0(5,1),q0(6,1)));
            qG = q0+1e-5*qdot;
            errorNewton = norm(E(qN(1,1),qN(2,1),qN(3,1),qN(4,1),qN(5,1),qN(6,1)));
            errorGradient = norm(E(qG(1,1),qG(2,1),qG(3,1),qG(4,1),qG(5,1),qG(6,1)));
            q0=qN;
            errnorm = errorNewton;
            if (det(Jval)<1e-2||abs(cos(q0(5,1)))<=1e-1) && errorNewton>errorGradient
                q0=qG;
                errnorm = errorGradient;
            end
            qik(1:6,end+1)=q0;
            % Stop Criteria
            if errnorm<1e-4
                qik(1:6,end+1)=q0;
                break;
            end
        end
    end
end



