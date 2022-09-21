function [p,dp,ddp]=quintic_spline(x,step)
sz = size(x,1);
qd = gradient(x,0.5)*0.5;
qdd = gradient(qd,0.5)*0.5;
% qd(qd>1)=1;
% qd(qd<-1)=-1;
% qdd = gradient(qd,0.5)*0.5;
% qdd(qdd>1)=1;
% qdd(qdd<-1)=-1;
qd(1,1)=0;
qdd(1,1)=0;
A=@(ti,tf1)[ti^5 ti^4 ti^3 ti^2 ti 1;
    tf1^5 tf1^4 tf1^3 tf1^2 tf1 1;
    5*ti^4 4*ti^3 3*ti^2 2*ti 1 0;
    5*tf1^4 4*tf1^3 3*tf1^2 2*tf1 1 0;
    20*ti^3 12*ti^2 6*ti 2 0 0;
    20*tf1^3 12*tf1^2 6*tf1 2 0 0];
% A =@(ti,tf1)[ti^3   ti^2  ti  1;
%             tf1^3   tf1^2  tf1 1;
%             3*ti^2  2*ti  1   0;
%             3*tf1^2 2*tf1 1   0];
b =@(pi,pf,vi,vf,ai,af) [pi;pf;vi;vf;ai;af];
% b =@(pi,pf,vi,vf) [pi;pf;vi;vf];

s =@(t,x) x(1)*t^5+x(2)*t^4+x(3)*t^3+x(4)*t^2+x(5)*t+x(6);
sdot =@(t,x) 5*x(1)*t^4+4*x(2)*t^3+3*x(3)*t^2+2*x(4)*t+x(5);
sddot =@(t,x) 20*x(1)*t^3+12*x(2)*t^2+6*x(3)*t+2*x(4);
% s =@(t,x) x(1)*t^3+x(2)*t^2+x(3)*t+x(4);
% sdot =@(t,x) 3*x(1)*t^2+2*x(2)*t+x(3);
% sddot =@(t,x) 6*x(1)*t+2*x(2);
p=double.empty;
dp=double.empty;
ddp=double.empty;
ts = 0;
pi = x(1);
vi=0;
ai=0;
    interval = ts:step:0.5;

for i = 1:sz-1
%     interval = ts:step:0.5;
    pf=x(i+1);
    vf = qd(i+1);
    af = qdd(i+1);
    if i == sz-1
        vf=0;
        af=0;
    end
    X = inv(A(interval(1),interval(end)))*b(pi,pf,vi,vf,ai,af);
%     X = inv(A(interval(1),interval(end)))*b(pi,pf,vi,vf);

    for tk = interval
        p(end+1)=s(tk,X);
        dp(end+1) = sdot(tk,X);
        ddp(end+1) = sddot(tk,X);
    end

    pi=p(end);
    vi=dp(end);
    ai=ddp(end);
    interval = interval+0.5;
end
end

