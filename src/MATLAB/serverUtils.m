classdef serverUtils < handle
    properties

    end

    methods
        function obj = serverUtils()
            import java.io.ByteArrayOutputStream;
            import java.io.ObjectOutputStream;
            import java.io.ByteArrayInputStream;
            import java.io.ByteArrayOutputStream;
            import java.io.ObjectInputStream;
            import java.io.IOException;
            import java.util.zip.GZIPInputStream;
            import java.util.zip.GZIPOutputStream;
        end
        function zipArray= compress(~,data)
            baos = java.io.ByteArrayOutputStream();
            gzipOut = java.util.zip.GZIPOutputStream(baos);
            objectOut = java.io.ObjectOutputStream(gzipOut);
            objectOut.writeObject(data);
            objectOut.close();
            zipArray = baos.toByteArray();
            gzipOut.finish()
            gzipOut.close();
        end
        function [data] = decompress(~,zipArray)
            bais = java.io.ByteArrayInputStream(zipArray);
            gzipIn = java.util.zip.GZIPInputStream(bais);
            objectIn = java.io.ObjectInputStream(gzipIn);
            data = objectIn.readObject();
            objectIn.close();
            gzipIn.close();
        end
        function byteArray = raw2byteArray(~,raw)
            bos = java.io.ByteArrayOutputStream();
            out = java.io.ObjectOutputStream(bos);
            out.writeObject(raw);
            out.flush();
            byteArray = bos.toByteArray();
            bos.close();
            out.close();
        end
        function raw = byteArray2Raw(~,byteArray)
            bais = java.io.ByteArrayInputStream(byteArray);
            ois = java.io.ObjectInputStream(bais);
            raw = ois.readObject();
            ois.close();
            bais.close();
        end
        function Output=rle(~,Input)
            countVariable =1;
            compressData1=zeros(10,1);

            r=1;

            compressData1 = reshape(Input',1,[]);
            for k=1:size(compressData1,2)

                if size(compressData1,2) == k
                    Output(1,r)=countVariable;
                    Output(1,r+1)=compressData1(1,k);

                elseif compressData1(1,k)== compressData1(1,k+1)
                    countVariable=countVariable+1;

                else
                    Output(1,r)=countVariable;
                    Output(1,r+1)=compressData1(1,k);
                    countVariable=1;
                    r=r+2;
                end
            end
        end

        function Output=irle(~,Input)
            L=length(Input);
            s=1;
            k=1;
            i=1;
            while i<=L
                while s<=Input(i+1)
                    Output(k)=Input(i);
                    s=s+1;
                    k=k+1;
                end
                i=i+2;
                s=1;
            end
        end

        function data = compressImg(obj,img)
            sz = size(img);
            if length(sz)==3
                M = permute(img,[3 2 1]);
                data = obj.compress(reshape(M,1,[]));
            else
                data=obj.compress(img);

            end
        end

        function sendMessage(obj,src,msg)
            toSend = obj.serialize(msg);
            write(src,toSend,"int8");
            flush(src);
        end
        function msg = buildMessage(~,msg,key,val)
            if msg == 0
                msg = javaObject('java.util.LinkedHashMap');
            end
            msg.put(key,val);
        end

        function r = deserialize(~,data)

            input = java.io.ByteArrayInputStream(data);
            ois = java.io.ObjectInputStream(input);
            r =(ois.readObject());

        end

        function p = serialize(~,data)

            byteOutputStream = java.io.ByteArrayOutputStream();
            dataOutputStream = java.io.ObjectOutputStream(byteOutputStream);
            dataOutputStream.writeObject(data);
            dataOutputStream.flush();
            byteOutputStream.flush();
            p = byteOutputStream.toByteArray();
            byteOutputStream.close();
        end
        function [alpha, red, green, blue] = convertImage(~,M)
            % M: matrix to convert. n x n x 3 or n x n;
            % k: boolean for left/right shifting. 1 is left, -1 is right.
            dim = size(M);
            if length(dim) == 3
                alpha = bitshift(ones(dim(1),dim(2))*255,24);
                red = bitshift(M(:,:,1),16,'int32');
                green = bitshift(M(:,:,2),8,'int32');
                blue = bitshift(M(:,:,3),0,'int32');
            else
                alpha = 0;
                red = bitand(bitshift(M,-16,'int32'),ones(dim(1),dim(2))*255,'int32');
                green = bitand(bitshift(M,-8,'int32'),ones(dim(1),dim(2))*255,'int32');
                blue = bitand(M,ones(dim(1),dim(2))*255,'int32');
            end
        end
    end
end