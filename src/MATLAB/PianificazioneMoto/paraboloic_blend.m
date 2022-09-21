function [q,dq,ddq,time]= paraboloic_blend(x,time)
N = length(x);
q = double.empty;
dq = double.empty;
ddq = double.empty;
pi = x(1);
t_interval = 0:1/100:0.5;
st = length(t_interval);
for i = 2:N
    pf = x(i);
    tf = t_interval(end);
    V = (x(i)-x(i-1))/(1)*1.5;

    if pi == pf
        for tk=2:st
            q(end+1)=pi;
            dq(end+1)=0;
            ddq(end+1)=0;
        end
    else
        tb =(pi-pf+V*(tf))/V;
        a = V/tb;
        
        for tk=2:st
            tt =t_interval(tk);
            if tt <= tb
                % initial blend
                q(end+1) = pi + a/2*tt^2;
                dq(end+1) = a*tt;
                ddq(end+1) = a;
            elseif tt <= (tf-tb)
                % linear motion
                q(end+1) = (pf+pi-V*tf)/2 + V*tt;
                dq(end+1) = V;
                ddq(end+1) = 0;
            else
                % final blend
                q(end+1) = pf - a/2*tf^2 + a*tf*tt - a/2*tt^2;
                dq(end+1) = a*tf - a*tt;
                ddq(end+1) = -a;
            end
        end
    end
    pi=pf;
end


end