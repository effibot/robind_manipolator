classdef compressionUtils < handle
    properties

    end

    methods
        function obj = compressionUtils()
            import java.io.ByteArrayOutputStream;
            import java.io.ObjectOutputStream;
            import java.io.ByteArrayInputStream;
            import java.io.ByteArrayOutputStream;
            import java.io.ObjectInputStream;
            import java.io.IOException;
            import java.util.zip.GZIPInputStream;
            import java.util.zip.GZIPOutputStream;
        end
        function [zipArray] = compress(~,data)
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
            L=length(Input);
            j=1;
            k=1;
            i=1;
            while i<2*L
                comp=1;
                for j=j:L
                    if j==L
                        break
                    end
                    if Input(j)==Input(j+1)
                        comp=comp+1;
                    else
                        break
                    end
                end
                Output(k+1)=comp;
                Output(k)=Input(j);
                if j==L && Input(j-1)==Input(j)
                    break
                end
                i=i+1;
                k=k+2;
                j=j+1;
                if j==L
                    if mod(L,2)==0
                        Output(k+1)=1;
                        Output(k)=Input(j);
                    else
                        Output(k+1)=1;
                        Output(k)=Input(j);
                    end
                    break
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

    end
end