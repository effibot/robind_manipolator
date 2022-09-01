function simg = showimage(img)
f=figure('Visible','off');
f.Position=[0,0,1024,1024];
f.Units='points';
simg=imshow(img,'Border','tight');
end
