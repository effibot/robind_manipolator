function [objArea,objPerim,objShape,orient,diag,obb,z, a, b, alpha] = computeGeometric(img,center)
syms diag_i(x) x
diag = sym.empty;

%% Determino Area e Perimetro dell'Oggetto nell'Immagine

% bwboundaries() determina i contorni di un oggetto in un'immagine b/w
% il parametro 'noholes' serve per specificare all'algoritmo di cercare
% soltanto oggetti compatti, velocizzando il processo.
% Tale algoritmo può identificare più oggetti in una singola immagine.
[B, L] = bwboundaries(img, 'noholes');
% regionprops() calcola il valore effettivo di perimetro ed area.
% Salvo il risultato in un oggetto.
props = regionprops(L,'BoundingBox','Centroid', 'Area', 'Perimeter','Orientation', 'MajorAxisLength', 'MinorAxisLength');
% Salvo Area e Perimetro di tutti gli oggetti identificati come lista.
areas = [props.Area];
perims = [props.Perimeter];
% Discrimino l'oggetto da identificare da tutti quelli nell'immagine
objArea = max(areas);
objPerim = max(perims);
% Sapendo che Area = (perimetro * apotema)/2 e che l'apotema è pari ad un
% numero fisso per perimetro/numero_lati, troviamo che
apothem = objArea*2/objPerim;
epsilon = 0.01;
fixed = [0.28867, 0.5, 0.68819, 0.86602];
polig = ["Triangolo", "Quadrilatero", "Pentagono", "Esagono"];
deltaP=zeros(size(polig));
deltaA=zeros(size(polig));
deltaFix=zeros(size(polig));
deltaAp=zeros(size(polig));
for i=1:length(polig)
    numLati = i+2;
% calcolo valori fissi sperimentali
% controllo quale valore fisso è più vicino
% Calcolo perimetro sperimentale 
    perimExp = apothem/fixed(i)*numLati;
    areaExp = apothem^2*numLati/fixed(i);
    fixExp = apothem*numLati/objPerim;
    apExp = 2*areaExp/perimExp;
% controllo quale perimetro più si avvicina a quello dell'oggetto
    deltaP(i) = min(abs(objPerim-perimExp));
    deltaA(i) = min(abs(objArea-areaExp));
    deltaFix(i) = min(abs(fixed(i)-fixExp));
    deltaAp(i) = min(abs(apothem-apExp));
% areaexp=perimExp*apothem/2
% abs(objArea-areaexp)
end
[~,idP] = min(deltaP);
[~,idA] = min(deltaA);
[~,idF] = min(deltaFix);
[~,idap] = min(deltaAp);
objShape = polig(mode([idP,idA,idF,idap]));

%% Eseguo Trasformata di Radon per determinare baricentro ed orientamento
% Calcoliamo le proiezioni, in modo tale da identificare le diagonali
% principali dell'oggetto da riconoscere. La loro intersezione ne
% determinerà il baricentro.
% Definisco Rispetto a quanti angoli effettuare la proiezione.
theta = 0:179;
% Eseguo le proiezioni.
% R è la trasformata, Xp l'angolo in radianti relativo alla trasformata.
[R1, xp1] = radon(img,  theta);
% Determino la proiezione con altezza maggiore per determinare la diagonale
maxRadon = max(R1);
%% Determino il massimo per identificare il punto più alto della
% proiezione con altezza maggiore.
% [pk, locs] = findpeaks() identifica il massimo locale del vettore dato in
% ingresso e l'indice di quel valore all'interno del vettore.
% SosrtStr specifica che i risultati andranno ordinati
% NPeaks specifica quanti massimi locali trovare nel vettore.
% figure
angleCut = (180*(numLati-2))/numLati;
[pk, locs] = findpeaks(maxRadon,'SortStr','descend',...
    'MinPeakHeight',max(maxRadon)*0.7,'MinPeakDistance',angleCut,'Threshold',1e-4);
% Scommentare per plottare l'output di findpeak
% findpeaks(maxRadon,'SortStr','descend','MinPeakHeight',max(maxRadon)*0.6,...
%     'MinPeakDistance',25,'Threshold',1e-4)
locs = locs - 1;
[M, idM] = max(pk);
%% Ellisse circoscritta all'oggetto
% trovo l'oggetto più grande nella fotoù
if length(B) == 1 
    obb = B{1};
elseif length(B) > 1
    maxi = 0;
    lenprev = length(B{1});
    for i = 2:length(B)
        if length(B{i}) > lenprev
            lenrev = length(B{i});
            maxi = i;
        end
    end
    obb = B{maxi};
end
% [z, a, b, alpha] = fitellipse([obb(:,2)';obb(:,1)'], 'linear', 'constraint', 'trace');
z = props.Centroid';
a = props.MajorAxisLength;
b = props.MinorAxisLength;
alpha = props.Orientation;
%% Calcolo diagonali
for i = 1:size(locs,2)
    theta_i = locs(i);
        offset = xp1(R1(:,locs(i)+1) == pk(i));
    diag_i(x) = -tand(theta_i+90)*(x-offset*cosd(theta_i)-center(1))-offset*sind(theta_i)+center(2);
    diag = horzcat(diag,diag_i(x));
end
orient = alpha+pi;
end

