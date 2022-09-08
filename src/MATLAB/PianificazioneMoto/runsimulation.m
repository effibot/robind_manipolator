function [qr,dqr,ddqr,e]=runsimulation(M,alpha,src)
% time = 0:step:(size(p,1)-1)/1000;
Fa = 10*M;
Kp = alpha^2;
Kd = 2*alpha;
load path.mat
step=1e-3;
time = 0:step:(size(p,1)-1)/1000;
pidm = 'PIDTrajectory';
load_system(pidm)
mdlWks = get_param(pidm,'ModelWorkspace');
assignin(mdlWks,'xp',p(:,1));
assignin(mdlWks,'yp',p(:,2));
assignin(mdlWks,'vxp',dp(1,:));
assignin(mdlWks,'vyp',dp(2,:));
assignin(mdlWks,'axp',ddp(1,:));
assignin(mdlWks,'ayp',ddp(2,:));
assignin(mdlWks,'x0',p(1,1));
assignin(mdlWks,'y0',p(1,2));
assignin(mdlWks,'v0x',dp(1,1));
assignin(mdlWks,'v0y',dp(2,1));
assignin(mdlWks,'Kp',Kp);
assignin(mdlWks,'Kd',Kd);
assignin(mdlWks,'step',step);
assignin(mdlWks,'Fa',Fa);
simout=sim(pidm,[time(1) time(end)]);
qr=simout.get('p');
dqr=simout.get('v');
ddqr=simout.get('a');
e=simout.get('e');
msg = buildMessage(0,"Qs",qr);
msg = buildMessage(msg,"dQs",dqr);
msg = buildMessage(msg,"ddQs",ddqr);
msg = buildMessage(msg,"E",e);
msg = buildMessage(msg,"FINISH",0);
sendMessage(src,msg);
save_system('PIDTrajectory');
close_system('PIDTrajectory');
runonmap(M,qr,rbclist,nodeList,robotsize,src);
msg = buildMessage(0,"FINISH",1);
sendMessage(src,msg);
end
