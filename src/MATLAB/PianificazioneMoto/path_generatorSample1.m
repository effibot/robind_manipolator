% Sample script to demonstrate execution of function [p, dp, ddp] = path_generator(startId, shapepos, method)
startId = 19; % Initialize startId here
shapepos = [700, 600]; % Initialize shapepos here
method = 'paraboloic'; % Initialize method here
[p, dp, ddp] = path_generator(startId, shapepos, method);
