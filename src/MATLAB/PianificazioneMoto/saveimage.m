function saveimage(gcf,path,name)
% relativePath = ['.\mapgenerationimg\',path];
absPath = [path,name];
f=getframe(gcf);
[XX, ~] = frame2im(f);
outfile = fullfile(path,name);
imwrite(XX,absPath,'png');
end