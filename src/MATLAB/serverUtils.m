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
            data = javaObject('java.util.ArrayList');
            if length(sz)==3
                r = img(:,:,1);
                g = img(:,:,2);
                b = img(:,:,3);
                rcomp = obj.rle(r);
                gcomp = obj.rle(g);
                bcomp = obj.rle(b);

                data.add(obj.compress(rcomp));
                data.add(obj.compress(gcomp));
                data.add(obj.compress(bcomp));
            else
                data=obj.compress(obj.rle(img));
            end
        end

        function sendMessage(obj,src,msg)
            toSend = obj.serialize(msg);
            write(src,toSend,"int8");
           flush(src);
        end
        function msg = buildMessage(~,msg,key,val)
            if msg == 0
                msg = javaObject('java.util.HashMap');
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

    end
end