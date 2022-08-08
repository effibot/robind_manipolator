% Sample script to demonstrate execution of function [p, dp, ddp] = path_generator(startId, shapepos, method)
startId = 3; % Initialize startId here
shapepos = [150, 150]; % Initialize shapepos here
method = 'paraboloic'; % Initialize method here
[p, dp, ddp] = path_generator(startId, shapepos, method);
