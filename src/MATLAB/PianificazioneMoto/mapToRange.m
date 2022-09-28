function r =  mapToRange(value,start1,  stop1, start2,  stop2) 
   r =start2 + (stop2 - start2) * ((value - start1) / (stop1 - start1));
end
