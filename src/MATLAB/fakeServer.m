clc
clear
close
%%
compressor = serverUtils;
server = tcpserver('localhost',9999);
server.UserData = compressor;
fib = @myFibonacci;
configureTerminator(server,255);
configureCallback(server,"terminator",fib);

function myFibonacci(src, ~)
    pkt = read(src, src.NumBytesAvailable, 'int8');
    pkt = src.UserData.deserialize(pkt);
    n = pkt.get("KEY");
    map = javaObject('java.util.HashMap');
    for i=0:1:n
        map.put("FIB", fibonacci(i));
        if i ~= n
            map.put("END",0);
        else 
            map.put("END", 1);        
        end
        src.UserData.sendMessage(src, map);
    end
end