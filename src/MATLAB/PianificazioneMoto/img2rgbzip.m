function [rzip,gzip,bzip] = img2rgbzip(img)
    R = rle(reshape(img(:,:,1),[],1));
    G = rle(reshape(img(:,:,2),[],1));
    B = rle(reshape(img(:,:,3),[],1));
    compressor = compressionUtils();
    rzip = compressor.compress(R);
    gzip = compressor.compress(G);
    bzip = compressor.compress(B);    
end