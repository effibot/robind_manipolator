function [p,dp,ddp]=cubic_spline(x,step)
sz = size(x,1);
qd = gradient(x,0.5)*0.5;
qdd = gradient(qd,0.5)*0.5;
qd(1,1)=0;
qdd(1,1)=0;
A =@(ti,tf1)[ti^3   ti^2  ti  1;
            tf1^3   tf1^2  tf1 1;
            3*ti^2  2*ti  1   0;
            3*tf1^2 2*tf1 1   0];
b =@(pi,pf,vi,vf) [pi;pf;vi;vf];
s =@(t,x) x(1)*t^3+x(2)*t^2+x(3)*t+x(4);
sdot =@(t,x) 3*x(1)*t^2+2*x(2)*t+x(3);
sddot =@(t,x) 6*x(1)*t+2*x(2);
p=double.empty;
dp=double.empty;
ddp=double.empty;
ts = 0;
pi = x(1);
vi=0;
interval = ts:step:0.5;

for i = 1:sz-1
    pf=x(i+1);
    vf = qd(i+1);
    af = qdd(i+1);
    if i == sz-1
        vf=0;
        af=0;
    end
    X = inv(A(interval(1),interval(end)))*b(pi,pf,vi,vf);

    for tk = interval
        p(end+1)=s(tk,X);
        dp(end+1) = sdot(tk,X);
        ddp(end+1) = sddot(tk,X);
    end

    pi=pf;
    vi=vf;
    interval=interval+step;
end
end

