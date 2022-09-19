#include "PIDTrajectory.h"
#include "rtwtypes.h"
#include "PIDTrajectory_private.h"
#include "rt_logging_mmi.h"
#include "PIDTrajectory_capi.h"
#include "PIDTrajectory_dt.h"
extern void * CreateDiagnosticAsVoidPtr_wrapper ( const char * id , int nargs
, ... ) ; RTWExtModeInfo * gblRTWExtModeInfo = NULL ; void
raccelForceExtModeShutdown ( boolean_T extModeStartPktReceived ) { if ( !
extModeStartPktReceived ) { boolean_T stopRequested = false ;
rtExtModeWaitForStartPkt ( gblRTWExtModeInfo , 2 , & stopRequested ) ; }
rtExtModeShutdown ( 2 ) ; }
#include "slsv_diagnostic_codegen_c_api.h"
#include "slsa_sim_engine.h"
const int_T gblNumToFiles = 0 ; const int_T gblNumFrFiles = 0 ; const int_T
gblNumFrWksBlocks = 6 ;
#ifdef RSIM_WITH_SOLVER_MULTITASKING
boolean_T gbl_raccel_isMultitasking = 1 ;
#else
boolean_T gbl_raccel_isMultitasking = 0 ;
#endif
boolean_T gbl_raccel_tid01eq = 1 ; int_T gbl_raccel_NumST = 3 ; const char_T
* gbl_raccel_Version = "9.7 (R2022a) 13-Nov-2021" ; void
raccel_setup_MMIStateLog ( SimStruct * S ) {
#ifdef UseMMIDataLogging
rt_FillStateSigInfoFromMMI ( ssGetRTWLogInfo ( S ) , & ssGetErrorStatus ( S )
) ;
#else
UNUSED_PARAMETER ( S ) ;
#endif
} static DataMapInfo rt_dataMapInfo ; DataMapInfo * rt_dataMapInfoPtr = &
rt_dataMapInfo ; rtwCAPI_ModelMappingInfo * rt_modelMapInfoPtr = & (
rt_dataMapInfo . mmi ) ; const int_T gblNumRootInportBlks = 0 ; const int_T
gblNumModelInputs = 0 ; extern const char * gblInportFileName ; extern
rtInportTUtable * gblInportTUtables ; const int_T gblInportDataTypeIdx [ ] =
{ - 1 } ; const int_T gblInportDims [ ] = { - 1 } ; const int_T
gblInportComplex [ ] = { - 1 } ; const int_T gblInportInterpoFlag [ ] = { - 1
} ; const int_T gblInportContinuous [ ] = { - 1 } ; int_T enableFcnCallFlag [
] = { 1 , 1 , 1 } ; const char * raccelLoadInputsAndAperiodicHitTimes (
SimStruct * S , const char * inportFileName , int * matFileFormat ) { return
rt_RAccelReadInportsMatFile ( S , inportFileName , matFileFormat ) ; }
#include "simstruc.h"
#include "fixedpoint.h"
#include "slsa_sim_engine.h"
#include "simtarget/slSimTgtSLExecSimBridge.h"
B rtB ; X rtX ; DW rtDW ; static SimStruct model_S ; SimStruct * const rtS =
& model_S ; void MdlInitialize ( void ) { rtX . bvq1aeovgd = rtP . v0x ; rtX
. pybolimsew = rtP . v0y ; rtX . p405gjuf1q = rtP . x0 ; rtX . cuvdeofxzs =
rtP . y0 ; } void MdlStart ( void ) { { bool externalInputIsInDatasetFormat =
false ; void * pISigstreamManager = rt_GetISigstreamManager ( rtS ) ;
rtwISigstreamManagerGetInputIsInDatasetFormat ( pISigstreamManager , &
externalInputIsInDatasetFormat ) ; if ( externalInputIsInDatasetFormat ) { }
} { { { bool isStreamoutAlreadyRegistered = false ; { sdiSignalSourceInfoU
srcInfo ; sdiLabelU loggedName = sdiGetLabelFromChars ( "Mux4" ) ; sdiLabelU
origSigName = sdiGetLabelFromChars ( "" ) ; sdiLabelU propName =
sdiGetLabelFromChars ( "Mux4" ) ; sdiLabelU blockPath = sdiGetLabelFromChars
( "PIDTrajectory/To Workspace4" ) ; sdiLabelU blockSID = sdiGetLabelFromChars
( "" ) ; sdiLabelU subPath = sdiGetLabelFromChars ( "" ) ; sdiDims sigDims ;
sdiLabelU sigName = sdiGetLabelFromChars ( "Mux4" ) ;
sdiAsyncRepoDataTypeHandle hDT = sdiAsyncRepoGetBuiltInDataTypeHandle (
DATA_TYPE_DOUBLE ) ; { sdiComplexity sigComplexity = REAL ;
sdiSampleTimeContinuity stCont = SAMPLE_TIME_CONTINUOUS ; int_T sigDimsArray
[ 1 ] = { 2 } ; sigDims . nDims = 1 ; sigDims . dimensions = sigDimsArray ;
srcInfo . numBlockPathElems = 1 ; srcInfo . fullBlockPath = ( sdiFullBlkPathU
) & blockPath ; srcInfo . SID = ( sdiSignalIDU ) & blockSID ; srcInfo .
subPath = subPath ; srcInfo . portIndex = 0 + 1 ; srcInfo . signalName =
sigName ; srcInfo . sigSourceUUID = 0 ; rtDW . ecofnqvfjg . AQHandles =
sdiStartAsyncioQueueCreation ( hDT , & srcInfo , rt_dataMapInfo . mmi .
InstanceMap . fullPath , "4e13e23c-df0e-49e1-b77a-b74758ed2de6" ,
sigComplexity , & sigDims , DIMENSIONS_MODE_FIXED , stCont , "" ) ;
sdiCompleteAsyncioQueueCreation ( rtDW . ecofnqvfjg . AQHandles , hDT , &
srcInfo ) ; if ( rtDW . ecofnqvfjg . AQHandles ) {
sdiSetSignalSampleTimeString ( rtDW . ecofnqvfjg . AQHandles , "0.001" ,
0.001 , ssGetTFinal ( rtS ) ) ; sdiSetSignalRefRate ( rtDW . ecofnqvfjg .
AQHandles , 0.0 ) ; sdiSetRunStartTime ( rtDW . ecofnqvfjg . AQHandles ,
ssGetTaskTime ( rtS , 1 ) ) ; sdiAsyncRepoSetSignalExportSettings ( rtDW .
ecofnqvfjg . AQHandles , 1 , 0 ) ; sdiAsyncRepoSetSignalExportName ( rtDW .
ecofnqvfjg . AQHandles , loggedName , origSigName , propName ) ;
sdiAsyncRepoSetBlockPathDomain ( rtDW . ecofnqvfjg . AQHandles ) ; }
sdiFreeLabel ( sigName ) ; sdiFreeLabel ( loggedName ) ; sdiFreeLabel (
origSigName ) ; sdiFreeLabel ( propName ) ; sdiFreeLabel ( blockPath ) ;
sdiFreeLabel ( blockSID ) ; sdiFreeLabel ( subPath ) ; } } if ( !
isStreamoutAlreadyRegistered ) { { sdiLabelU varName = sdiGetLabelFromChars (
"p" ) ; sdiRegisterWksVariable ( rtDW . ecofnqvfjg . AQHandles , varName ,
"array" ) ; sdiFreeLabel ( varName ) ; } } } } } { { { bool
isStreamoutAlreadyRegistered = false ; { sdiSignalSourceInfoU srcInfo ;
sdiLabelU loggedName = sdiGetLabelFromChars ( "Mux5" ) ; sdiLabelU
origSigName = sdiGetLabelFromChars ( "" ) ; sdiLabelU propName =
sdiGetLabelFromChars ( "Mux5" ) ; sdiLabelU blockPath = sdiGetLabelFromChars
( "PIDTrajectory/To Workspace5" ) ; sdiLabelU blockSID = sdiGetLabelFromChars
( "" ) ; sdiLabelU subPath = sdiGetLabelFromChars ( "" ) ; sdiDims sigDims ;
sdiLabelU sigName = sdiGetLabelFromChars ( "Mux5" ) ;
sdiAsyncRepoDataTypeHandle hDT = sdiAsyncRepoGetBuiltInDataTypeHandle (
DATA_TYPE_DOUBLE ) ; { sdiComplexity sigComplexity = REAL ;
sdiSampleTimeContinuity stCont = SAMPLE_TIME_CONTINUOUS ; int_T sigDimsArray
[ 1 ] = { 2 } ; sigDims . nDims = 1 ; sigDims . dimensions = sigDimsArray ;
srcInfo . numBlockPathElems = 1 ; srcInfo . fullBlockPath = ( sdiFullBlkPathU
) & blockPath ; srcInfo . SID = ( sdiSignalIDU ) & blockSID ; srcInfo .
subPath = subPath ; srcInfo . portIndex = 0 + 1 ; srcInfo . signalName =
sigName ; srcInfo . sigSourceUUID = 0 ; rtDW . eiskowcg51 . AQHandles =
sdiStartAsyncioQueueCreation ( hDT , & srcInfo , rt_dataMapInfo . mmi .
InstanceMap . fullPath , "2f7ac993-5988-4af6-9ac0-345c987bfc5d" ,
sigComplexity , & sigDims , DIMENSIONS_MODE_FIXED , stCont , "" ) ;
sdiCompleteAsyncioQueueCreation ( rtDW . eiskowcg51 . AQHandles , hDT , &
srcInfo ) ; if ( rtDW . eiskowcg51 . AQHandles ) {
sdiSetSignalSampleTimeString ( rtDW . eiskowcg51 . AQHandles , "0.001" ,
0.001 , ssGetTFinal ( rtS ) ) ; sdiSetSignalRefRate ( rtDW . eiskowcg51 .
AQHandles , 0.0 ) ; sdiSetRunStartTime ( rtDW . eiskowcg51 . AQHandles ,
ssGetTaskTime ( rtS , 1 ) ) ; sdiAsyncRepoSetSignalExportSettings ( rtDW .
eiskowcg51 . AQHandles , 1 , 0 ) ; sdiAsyncRepoSetSignalExportName ( rtDW .
eiskowcg51 . AQHandles , loggedName , origSigName , propName ) ;
sdiAsyncRepoSetBlockPathDomain ( rtDW . eiskowcg51 . AQHandles ) ; }
sdiFreeLabel ( sigName ) ; sdiFreeLabel ( loggedName ) ; sdiFreeLabel (
origSigName ) ; sdiFreeLabel ( propName ) ; sdiFreeLabel ( blockPath ) ;
sdiFreeLabel ( blockSID ) ; sdiFreeLabel ( subPath ) ; } } if ( !
isStreamoutAlreadyRegistered ) { { sdiLabelU varName = sdiGetLabelFromChars (
"v" ) ; sdiRegisterWksVariable ( rtDW . eiskowcg51 . AQHandles , varName ,
"array2D" ) ; sdiFreeLabel ( varName ) ; } } } } } { { { bool
isStreamoutAlreadyRegistered = false ; { sdiSignalSourceInfoU srcInfo ;
sdiLabelU loggedName = sdiGetLabelFromChars ( "Mux6" ) ; sdiLabelU
origSigName = sdiGetLabelFromChars ( "" ) ; sdiLabelU propName =
sdiGetLabelFromChars ( "Mux6" ) ; sdiLabelU blockPath = sdiGetLabelFromChars
( "PIDTrajectory/To Workspace6" ) ; sdiLabelU blockSID = sdiGetLabelFromChars
( "" ) ; sdiLabelU subPath = sdiGetLabelFromChars ( "" ) ; sdiDims sigDims ;
sdiLabelU sigName = sdiGetLabelFromChars ( "Mux6" ) ;
sdiAsyncRepoDataTypeHandle hDT = sdiAsyncRepoGetBuiltInDataTypeHandle (
DATA_TYPE_DOUBLE ) ; { sdiComplexity sigComplexity = REAL ;
sdiSampleTimeContinuity stCont = SAMPLE_TIME_CONTINUOUS ; int_T sigDimsArray
[ 1 ] = { 2 } ; sigDims . nDims = 1 ; sigDims . dimensions = sigDimsArray ;
srcInfo . numBlockPathElems = 1 ; srcInfo . fullBlockPath = ( sdiFullBlkPathU
) & blockPath ; srcInfo . SID = ( sdiSignalIDU ) & blockSID ; srcInfo .
subPath = subPath ; srcInfo . portIndex = 0 + 1 ; srcInfo . signalName =
sigName ; srcInfo . sigSourceUUID = 0 ; rtDW . lseu3eofyv . AQHandles =
sdiStartAsyncioQueueCreation ( hDT , & srcInfo , rt_dataMapInfo . mmi .
InstanceMap . fullPath , "23c4c0f7-d701-4e41-b0e3-96412c282ec6" ,
sigComplexity , & sigDims , DIMENSIONS_MODE_FIXED , stCont , "" ) ;
sdiCompleteAsyncioQueueCreation ( rtDW . lseu3eofyv . AQHandles , hDT , &
srcInfo ) ; if ( rtDW . lseu3eofyv . AQHandles ) {
sdiSetSignalSampleTimeString ( rtDW . lseu3eofyv . AQHandles , "0.001" ,
0.001 , ssGetTFinal ( rtS ) ) ; sdiSetSignalRefRate ( rtDW . lseu3eofyv .
AQHandles , 0.0 ) ; sdiSetRunStartTime ( rtDW . lseu3eofyv . AQHandles ,
ssGetTaskTime ( rtS , 1 ) ) ; sdiAsyncRepoSetSignalExportSettings ( rtDW .
lseu3eofyv . AQHandles , 1 , 0 ) ; sdiAsyncRepoSetSignalExportName ( rtDW .
lseu3eofyv . AQHandles , loggedName , origSigName , propName ) ;
sdiAsyncRepoSetBlockPathDomain ( rtDW . lseu3eofyv . AQHandles ) ; }
sdiFreeLabel ( sigName ) ; sdiFreeLabel ( loggedName ) ; sdiFreeLabel (
origSigName ) ; sdiFreeLabel ( propName ) ; sdiFreeLabel ( blockPath ) ;
sdiFreeLabel ( blockSID ) ; sdiFreeLabel ( subPath ) ; } } if ( !
isStreamoutAlreadyRegistered ) { { sdiLabelU varName = sdiGetLabelFromChars (
"a" ) ; sdiRegisterWksVariable ( rtDW . lseu3eofyv . AQHandles , varName ,
"array" ) ; sdiFreeLabel ( varName ) ; } } } } } { { { bool
isStreamoutAlreadyRegistered = false ; { sdiSignalSourceInfoU srcInfo ;
sdiLabelU loggedName = sdiGetLabelFromChars ( "Mux7" ) ; sdiLabelU
origSigName = sdiGetLabelFromChars ( "" ) ; sdiLabelU propName =
sdiGetLabelFromChars ( "Mux7" ) ; sdiLabelU blockPath = sdiGetLabelFromChars
( "PIDTrajectory/To Workspace7" ) ; sdiLabelU blockSID = sdiGetLabelFromChars
( "" ) ; sdiLabelU subPath = sdiGetLabelFromChars ( "" ) ; sdiDims sigDims ;
sdiLabelU sigName = sdiGetLabelFromChars ( "Mux7" ) ;
sdiAsyncRepoDataTypeHandle hDT = sdiAsyncRepoGetBuiltInDataTypeHandle (
DATA_TYPE_DOUBLE ) ; { sdiComplexity sigComplexity = REAL ;
sdiSampleTimeContinuity stCont = SAMPLE_TIME_CONTINUOUS ; int_T sigDimsArray
[ 1 ] = { 4 } ; sigDims . nDims = 1 ; sigDims . dimensions = sigDimsArray ;
srcInfo . numBlockPathElems = 1 ; srcInfo . fullBlockPath = ( sdiFullBlkPathU
) & blockPath ; srcInfo . SID = ( sdiSignalIDU ) & blockSID ; srcInfo .
subPath = subPath ; srcInfo . portIndex = 0 + 1 ; srcInfo . signalName =
sigName ; srcInfo . sigSourceUUID = 0 ; rtDW . dbtjzghbsv . AQHandles =
sdiStartAsyncioQueueCreation ( hDT , & srcInfo , rt_dataMapInfo . mmi .
InstanceMap . fullPath , "510d3508-0452-4742-b037-c70f28139feb" ,
sigComplexity , & sigDims , DIMENSIONS_MODE_FIXED , stCont , "" ) ;
sdiCompleteAsyncioQueueCreation ( rtDW . dbtjzghbsv . AQHandles , hDT , &
srcInfo ) ; if ( rtDW . dbtjzghbsv . AQHandles ) {
sdiSetSignalSampleTimeString ( rtDW . dbtjzghbsv . AQHandles , "0.001" ,
0.001 , ssGetTFinal ( rtS ) ) ; sdiSetSignalRefRate ( rtDW . dbtjzghbsv .
AQHandles , 0.0 ) ; sdiSetRunStartTime ( rtDW . dbtjzghbsv . AQHandles ,
ssGetTaskTime ( rtS , 1 ) ) ; sdiAsyncRepoSetSignalExportSettings ( rtDW .
dbtjzghbsv . AQHandles , 1 , 0 ) ; sdiAsyncRepoSetSignalExportName ( rtDW .
dbtjzghbsv . AQHandles , loggedName , origSigName , propName ) ;
sdiAsyncRepoSetBlockPathDomain ( rtDW . dbtjzghbsv . AQHandles ) ; }
sdiFreeLabel ( sigName ) ; sdiFreeLabel ( loggedName ) ; sdiFreeLabel (
origSigName ) ; sdiFreeLabel ( propName ) ; sdiFreeLabel ( blockPath ) ;
sdiFreeLabel ( blockSID ) ; sdiFreeLabel ( subPath ) ; } } if ( !
isStreamoutAlreadyRegistered ) { { sdiLabelU varName = sdiGetLabelFromChars (
"e" ) ; sdiRegisterWksVariable ( rtDW . dbtjzghbsv . AQHandles , varName ,
"array" ) ; sdiFreeLabel ( varName ) ; } } } } } { FWksInfo * fromwksInfo ;
if ( ( fromwksInfo = ( FWksInfo * ) calloc ( 1 , sizeof ( FWksInfo ) ) ) == (
NULL ) ) { ssSetErrorStatus ( rtS ,
"from workspace STRING(Name) memory allocation error" ) ; } else {
fromwksInfo -> origWorkspaceVarName = "s" ; fromwksInfo -> origDataTypeId = 0
; fromwksInfo -> origIsComplex = 0 ; fromwksInfo -> origWidth = 1 ;
fromwksInfo -> origElSize = sizeof ( real_T ) ; fromwksInfo -> data = ( void
* ) rtP . FromWorkspace_Data0 ; fromwksInfo -> nDataPoints = 7015 ;
fromwksInfo -> time = ( NULL ) ; rtDW . e1dw0gdlsu . TimePtr = fromwksInfo ->
time ; rtDW . e1dw0gdlsu . DataPtr = fromwksInfo -> data ; rtDW . e1dw0gdlsu
. RSimInfoPtr = fromwksInfo ; } rtDW . gvmkanlbst . PrevIndex = - 1 ; } {
FWksInfo * fromwksInfo ; if ( ( fromwksInfo = ( FWksInfo * ) calloc ( 1 ,
sizeof ( FWksInfo ) ) ) == ( NULL ) ) { ssSetErrorStatus ( rtS ,
"from workspace STRING(Name) memory allocation error" ) ; } else {
fromwksInfo -> origWorkspaceVarName = "s" ; fromwksInfo -> origDataTypeId = 0
; fromwksInfo -> origIsComplex = 0 ; fromwksInfo -> origWidth = 1 ;
fromwksInfo -> origElSize = sizeof ( real_T ) ; fromwksInfo -> data = ( void
* ) rtP . FromWorkspace_Data0_erdsjhbktd ; fromwksInfo -> nDataPoints = 7014
; fromwksInfo -> time = ( NULL ) ; rtDW . loho3tdcc3 . TimePtr = fromwksInfo
-> time ; rtDW . loho3tdcc3 . DataPtr = fromwksInfo -> data ; rtDW .
loho3tdcc3 . RSimInfoPtr = fromwksInfo ; } rtDW . e0x50jtzxz . PrevIndex = -
1 ; } { FWksInfo * fromwksInfo ; if ( ( fromwksInfo = ( FWksInfo * ) calloc (
1 , sizeof ( FWksInfo ) ) ) == ( NULL ) ) { ssSetErrorStatus ( rtS ,
"from workspace STRING(Name) memory allocation error" ) ; } else {
fromwksInfo -> origWorkspaceVarName = "s" ; fromwksInfo -> origDataTypeId = 0
; fromwksInfo -> origIsComplex = 0 ; fromwksInfo -> origWidth = 1 ;
fromwksInfo -> origElSize = sizeof ( real_T ) ; fromwksInfo -> data = ( void
* ) rtP . FromWorkspace_Data0_irypaqsgbf ; fromwksInfo -> nDataPoints = 7014
; fromwksInfo -> time = ( NULL ) ; rtDW . b1pf4c5bw1 . TimePtr = fromwksInfo
-> time ; rtDW . b1pf4c5bw1 . DataPtr = fromwksInfo -> data ; rtDW .
b1pf4c5bw1 . RSimInfoPtr = fromwksInfo ; } rtDW . e0ljdij4dh . PrevIndex = -
1 ; } { FWksInfo * fromwksInfo ; if ( ( fromwksInfo = ( FWksInfo * ) calloc (
1 , sizeof ( FWksInfo ) ) ) == ( NULL ) ) { ssSetErrorStatus ( rtS ,
"from workspace STRING(Name) memory allocation error" ) ; } else {
fromwksInfo -> origWorkspaceVarName = "s" ; fromwksInfo -> origDataTypeId = 0
; fromwksInfo -> origIsComplex = 0 ; fromwksInfo -> origWidth = 1 ;
fromwksInfo -> origElSize = sizeof ( real_T ) ; fromwksInfo -> data = ( void
* ) rtP . FromWorkspace_Data0_fbwrwi4aj1 ; fromwksInfo -> nDataPoints = 7015
; fromwksInfo -> time = ( NULL ) ; rtDW . ejxqv5alzd . TimePtr = fromwksInfo
-> time ; rtDW . ejxqv5alzd . DataPtr = fromwksInfo -> data ; rtDW .
ejxqv5alzd . RSimInfoPtr = fromwksInfo ; } rtDW . aeaptdbisy . PrevIndex = -
1 ; } { FWksInfo * fromwksInfo ; if ( ( fromwksInfo = ( FWksInfo * ) calloc (
1 , sizeof ( FWksInfo ) ) ) == ( NULL ) ) { ssSetErrorStatus ( rtS ,
"from workspace STRING(Name) memory allocation error" ) ; } else {
fromwksInfo -> origWorkspaceVarName = "s" ; fromwksInfo -> origDataTypeId = 0
; fromwksInfo -> origIsComplex = 0 ; fromwksInfo -> origWidth = 1 ;
fromwksInfo -> origElSize = sizeof ( real_T ) ; fromwksInfo -> data = ( void
* ) rtP . FromWorkspace_Data0_elsudc4efs ; fromwksInfo -> nDataPoints = 7014
; fromwksInfo -> time = ( NULL ) ; rtDW . d4sbxigd01 . TimePtr = fromwksInfo
-> time ; rtDW . d4sbxigd01 . DataPtr = fromwksInfo -> data ; rtDW .
d4sbxigd01 . RSimInfoPtr = fromwksInfo ; } rtDW . mhiwfrfp0y . PrevIndex = -
1 ; } { FWksInfo * fromwksInfo ; if ( ( fromwksInfo = ( FWksInfo * ) calloc (
1 , sizeof ( FWksInfo ) ) ) == ( NULL ) ) { ssSetErrorStatus ( rtS ,
"from workspace STRING(Name) memory allocation error" ) ; } else {
fromwksInfo -> origWorkspaceVarName = "s" ; fromwksInfo -> origDataTypeId = 0
; fromwksInfo -> origIsComplex = 0 ; fromwksInfo -> origWidth = 1 ;
fromwksInfo -> origElSize = sizeof ( real_T ) ; fromwksInfo -> data = ( void
* ) rtP . FromWorkspace_Data0_a2gi03qt2b ; fromwksInfo -> nDataPoints = 7014
; fromwksInfo -> time = ( NULL ) ; rtDW . pc2nyef1qt . TimePtr = fromwksInfo
-> time ; rtDW . pc2nyef1qt . DataPtr = fromwksInfo -> data ; rtDW .
pc2nyef1qt . RSimInfoPtr = fromwksInfo ; } rtDW . imsjzof1gf . PrevIndex = -
1 ; } MdlInitialize ( ) ; } void MdlOutputs ( int_T tid ) { real_T au5ak3shgm
[ 4 ] ; real_T jh2j2gfphf [ 2 ] ; rtB . dzhv4gakca = rtX . bvq1aeovgd ; rtB .
ayn2l5kx4f = rtX . pybolimsew ; if ( ssIsSampleHit ( rtS , 1 , 0 ) ) { } rtB
. ebdsuq3ovc = rtX . p405gjuf1q ; rtB . of4hv5yd2x = rtX . cuvdeofxzs ; if (
ssIsSampleHit ( rtS , 1 , 0 ) ) { jh2j2gfphf [ 0 ] = rtB . ebdsuq3ovc ;
jh2j2gfphf [ 1 ] = rtB . of4hv5yd2x ; { if ( rtDW . ecofnqvfjg . AQHandles &&
ssGetLogOutput ( rtS ) ) { sdiWriteSignal ( rtDW . ecofnqvfjg . AQHandles ,
ssGetTaskTime ( rtS , 1 ) , ( char * ) & jh2j2gfphf [ 0 ] + 0 ) ; } }
jh2j2gfphf [ 0 ] = rtB . dzhv4gakca ; jh2j2gfphf [ 1 ] = rtB . ayn2l5kx4f ; {
if ( rtDW . eiskowcg51 . AQHandles && ssGetLogOutput ( rtS ) ) {
sdiWriteSignal ( rtDW . eiskowcg51 . AQHandles , ssGetTaskTime ( rtS , 1 ) ,
( char * ) & jh2j2gfphf [ 0 ] + 0 ) ; } } { int_T currIndex = rtDW .
gvmkanlbst . PrevIndex + 1 ; real_T * pDataValues = ( real_T * ) rtDW .
e1dw0gdlsu . DataPtr ; int numPoints ; FWksInfo * fromwksInfo = ( FWksInfo *
) rtDW . e1dw0gdlsu . RSimInfoPtr ; numPoints = fromwksInfo -> nDataPoints ;
if ( currIndex < numPoints ) { pDataValues += currIndex ; rtB . lqgo1ctcu0 =
* pDataValues ; } else { pDataValues += ( numPoints - 1 ) ; rtB . lqgo1ctcu0
= * pDataValues ; } rtDW . gvmkanlbst . PrevIndex = currIndex ; } } rtB .
fts1qnwwnm = rtB . ebdsuq3ovc - rtB . lqgo1ctcu0 ; if ( ssIsSampleHit ( rtS ,
1 , 0 ) ) { { int_T currIndex = rtDW . e0x50jtzxz . PrevIndex + 1 ; real_T *
pDataValues = ( real_T * ) rtDW . loho3tdcc3 . DataPtr ; int numPoints ;
FWksInfo * fromwksInfo = ( FWksInfo * ) rtDW . loho3tdcc3 . RSimInfoPtr ;
numPoints = fromwksInfo -> nDataPoints ; if ( currIndex < numPoints ) {
pDataValues += currIndex ; rtB . bcl5o0t22v = * pDataValues ; } else { rtB .
bcl5o0t22v = 0.0 ; } rtDW . e0x50jtzxz . PrevIndex = currIndex ; } } rtB .
dn3s1knom1 = rtB . dzhv4gakca - rtB . bcl5o0t22v ; if ( ssIsSampleHit ( rtS ,
1 , 0 ) ) { { int_T currIndex = rtDW . e0ljdij4dh . PrevIndex + 1 ; real_T *
pDataValues = ( real_T * ) rtDW . b1pf4c5bw1 . DataPtr ; int numPoints ;
FWksInfo * fromwksInfo = ( FWksInfo * ) rtDW . b1pf4c5bw1 . RSimInfoPtr ;
numPoints = fromwksInfo -> nDataPoints ; if ( currIndex < numPoints ) {
pDataValues += currIndex ; rtB . ceh1qr02vf = * pDataValues ; } else { rtB .
ceh1qr02vf = 0.0 ; } rtDW . e0ljdij4dh . PrevIndex = currIndex ; } } rtB .
ekmphufo03 = ( ( rtP . Kp * rtB . fts1qnwwnm + rtP . Kd * rtB . dn3s1knom1 )
* rtP . Gain1_Gain + rtB . ceh1qr02vf ) - rtP . Fa ; if ( ssIsSampleHit ( rtS
, 1 , 0 ) ) { { int_T currIndex = rtDW . aeaptdbisy . PrevIndex + 1 ; real_T
* pDataValues = ( real_T * ) rtDW . ejxqv5alzd . DataPtr ; int numPoints ;
FWksInfo * fromwksInfo = ( FWksInfo * ) rtDW . ejxqv5alzd . RSimInfoPtr ;
numPoints = fromwksInfo -> nDataPoints ; if ( currIndex < numPoints ) {
pDataValues += currIndex ; rtB . bxc5wbeagz = * pDataValues ; } else {
pDataValues += ( numPoints - 1 ) ; rtB . bxc5wbeagz = * pDataValues ; } rtDW
. aeaptdbisy . PrevIndex = currIndex ; } } rtB . bqqkaib5ws = rtB .
of4hv5yd2x - rtB . bxc5wbeagz ; if ( ssIsSampleHit ( rtS , 1 , 0 ) ) { {
int_T currIndex = rtDW . mhiwfrfp0y . PrevIndex + 1 ; real_T * pDataValues =
( real_T * ) rtDW . d4sbxigd01 . DataPtr ; int numPoints ; FWksInfo *
fromwksInfo = ( FWksInfo * ) rtDW . d4sbxigd01 . RSimInfoPtr ; numPoints =
fromwksInfo -> nDataPoints ; if ( currIndex < numPoints ) { pDataValues +=
currIndex ; rtB . erdbg52csk = * pDataValues ; } else { rtB . erdbg52csk =
0.0 ; } rtDW . mhiwfrfp0y . PrevIndex = currIndex ; } } rtB . fukvwwoumx =
rtB . ayn2l5kx4f - rtB . erdbg52csk ; if ( ssIsSampleHit ( rtS , 1 , 0 ) ) {
{ int_T currIndex = rtDW . imsjzof1gf . PrevIndex + 1 ; real_T * pDataValues
= ( real_T * ) rtDW . pc2nyef1qt . DataPtr ; int numPoints ; FWksInfo *
fromwksInfo = ( FWksInfo * ) rtDW . pc2nyef1qt . RSimInfoPtr ; numPoints =
fromwksInfo -> nDataPoints ; if ( currIndex < numPoints ) { pDataValues +=
currIndex ; rtB . fo23lqib5q = * pDataValues ; } else { rtB . fo23lqib5q =
0.0 ; } rtDW . imsjzof1gf . PrevIndex = currIndex ; } } rtB . mpinfjn4a5 = (
( rtP . Kp * rtB . bqqkaib5ws + rtP . Kd * rtB . fukvwwoumx ) * rtP .
Gain1_Gain_ipqblw4zv4 + rtB . fo23lqib5q ) - rtP . Fa ; if ( ssIsSampleHit (
rtS , 1 , 0 ) ) { jh2j2gfphf [ 0 ] = rtB . ekmphufo03 ; jh2j2gfphf [ 1 ] =
rtB . mpinfjn4a5 ; { if ( rtDW . lseu3eofyv . AQHandles && ssGetLogOutput (
rtS ) ) { sdiWriteSignal ( rtDW . lseu3eofyv . AQHandles , ssGetTaskTime (
rtS , 1 ) , ( char * ) & jh2j2gfphf [ 0 ] + 0 ) ; } } au5ak3shgm [ 0 ] = rtB
. fts1qnwwnm ; au5ak3shgm [ 1 ] = rtB . bqqkaib5ws ; au5ak3shgm [ 2 ] = rtB .
dn3s1knom1 ; au5ak3shgm [ 3 ] = rtB . fukvwwoumx ; { if ( rtDW . dbtjzghbsv .
AQHandles && ssGetLogOutput ( rtS ) ) { sdiWriteSignal ( rtDW . dbtjzghbsv .
AQHandles , ssGetTaskTime ( rtS , 1 ) , ( char * ) & au5ak3shgm [ 0 ] + 0 ) ;
} } } UNUSED_PARAMETER ( tid ) ; } void MdlOutputsTID2 ( int_T tid ) {
UNUSED_PARAMETER ( tid ) ; } void MdlUpdate ( int_T tid ) { UNUSED_PARAMETER
( tid ) ; } void MdlUpdateTID2 ( int_T tid ) { UNUSED_PARAMETER ( tid ) ; }
void MdlDerivatives ( void ) { XDot * _rtXdot ; _rtXdot = ( ( XDot * )
ssGetdX ( rtS ) ) ; _rtXdot -> bvq1aeovgd = rtB . ekmphufo03 ; _rtXdot ->
pybolimsew = rtB . mpinfjn4a5 ; _rtXdot -> p405gjuf1q = rtB . dzhv4gakca ;
_rtXdot -> cuvdeofxzs = rtB . ayn2l5kx4f ; } void MdlProjection ( void ) { }
void MdlTerminate ( void ) { rt_FREE ( rtDW . e1dw0gdlsu . RSimInfoPtr ) ;
rt_FREE ( rtDW . loho3tdcc3 . RSimInfoPtr ) ; rt_FREE ( rtDW . b1pf4c5bw1 .
RSimInfoPtr ) ; rt_FREE ( rtDW . ejxqv5alzd . RSimInfoPtr ) ; rt_FREE ( rtDW
. d4sbxigd01 . RSimInfoPtr ) ; rt_FREE ( rtDW . pc2nyef1qt . RSimInfoPtr ) ;
{ if ( rtDW . ecofnqvfjg . AQHandles ) { sdiTerminateStreaming ( & rtDW .
ecofnqvfjg . AQHandles ) ; } } { if ( rtDW . eiskowcg51 . AQHandles ) {
sdiTerminateStreaming ( & rtDW . eiskowcg51 . AQHandles ) ; } } { if ( rtDW .
lseu3eofyv . AQHandles ) { sdiTerminateStreaming ( & rtDW . lseu3eofyv .
AQHandles ) ; } } { if ( rtDW . dbtjzghbsv . AQHandles ) {
sdiTerminateStreaming ( & rtDW . dbtjzghbsv . AQHandles ) ; } } } static void
mr_PIDTrajectory_cacheDataAsMxArray ( mxArray * destArray , mwIndex i , int j
, const void * srcData , size_t numBytes ) ; static void
mr_PIDTrajectory_cacheDataAsMxArray ( mxArray * destArray , mwIndex i , int j
, const void * srcData , size_t numBytes ) { mxArray * newArray =
mxCreateUninitNumericMatrix ( ( size_t ) 1 , numBytes , mxUINT8_CLASS ,
mxREAL ) ; memcpy ( ( uint8_T * ) mxGetData ( newArray ) , ( const uint8_T *
) srcData , numBytes ) ; mxSetFieldByNumber ( destArray , i , j , newArray )
; } static void mr_PIDTrajectory_restoreDataFromMxArray ( void * destData ,
const mxArray * srcArray , mwIndex i , int j , size_t numBytes ) ; static
void mr_PIDTrajectory_restoreDataFromMxArray ( void * destData , const
mxArray * srcArray , mwIndex i , int j , size_t numBytes ) { memcpy ( (
uint8_T * ) destData , ( const uint8_T * ) mxGetData ( mxGetFieldByNumber (
srcArray , i , j ) ) , numBytes ) ; } static void
mr_PIDTrajectory_cacheBitFieldToMxArray ( mxArray * destArray , mwIndex i ,
int j , uint_T bitVal ) ; static void mr_PIDTrajectory_cacheBitFieldToMxArray
( mxArray * destArray , mwIndex i , int j , uint_T bitVal ) {
mxSetFieldByNumber ( destArray , i , j , mxCreateDoubleScalar ( ( double )
bitVal ) ) ; } static uint_T mr_PIDTrajectory_extractBitFieldFromMxArray (
const mxArray * srcArray , mwIndex i , int j , uint_T numBits ) ; static
uint_T mr_PIDTrajectory_extractBitFieldFromMxArray ( const mxArray * srcArray
, mwIndex i , int j , uint_T numBits ) { const uint_T varVal = ( uint_T )
mxGetScalar ( mxGetFieldByNumber ( srcArray , i , j ) ) ; return varVal & ( (
1u << numBits ) - 1u ) ; } static void
mr_PIDTrajectory_cacheDataToMxArrayWithOffset ( mxArray * destArray , mwIndex
i , int j , mwIndex offset , const void * srcData , size_t numBytes ) ;
static void mr_PIDTrajectory_cacheDataToMxArrayWithOffset ( mxArray *
destArray , mwIndex i , int j , mwIndex offset , const void * srcData ,
size_t numBytes ) { uint8_T * varData = ( uint8_T * ) mxGetData (
mxGetFieldByNumber ( destArray , i , j ) ) ; memcpy ( ( uint8_T * ) & varData
[ offset * numBytes ] , ( const uint8_T * ) srcData , numBytes ) ; } static
void mr_PIDTrajectory_restoreDataFromMxArrayWithOffset ( void * destData ,
const mxArray * srcArray , mwIndex i , int j , mwIndex offset , size_t
numBytes ) ; static void mr_PIDTrajectory_restoreDataFromMxArrayWithOffset (
void * destData , const mxArray * srcArray , mwIndex i , int j , mwIndex
offset , size_t numBytes ) { const uint8_T * varData = ( const uint8_T * )
mxGetData ( mxGetFieldByNumber ( srcArray , i , j ) ) ; memcpy ( ( uint8_T *
) destData , ( const uint8_T * ) & varData [ offset * numBytes ] , numBytes )
; } static void mr_PIDTrajectory_cacheBitFieldToCellArrayWithOffset ( mxArray
* destArray , mwIndex i , int j , mwIndex offset , uint_T fieldVal ) ; static
void mr_PIDTrajectory_cacheBitFieldToCellArrayWithOffset ( mxArray *
destArray , mwIndex i , int j , mwIndex offset , uint_T fieldVal ) {
mxSetCell ( mxGetFieldByNumber ( destArray , i , j ) , offset ,
mxCreateDoubleScalar ( ( double ) fieldVal ) ) ; } static uint_T
mr_PIDTrajectory_extractBitFieldFromCellArrayWithOffset ( const mxArray *
srcArray , mwIndex i , int j , mwIndex offset , uint_T numBits ) ; static
uint_T mr_PIDTrajectory_extractBitFieldFromCellArrayWithOffset ( const
mxArray * srcArray , mwIndex i , int j , mwIndex offset , uint_T numBits ) {
const uint_T fieldVal = ( uint_T ) mxGetScalar ( mxGetCell (
mxGetFieldByNumber ( srcArray , i , j ) , offset ) ) ; return fieldVal & ( (
1u << numBits ) - 1u ) ; } mxArray * mr_PIDTrajectory_GetDWork ( ) { static
const char * ssDWFieldNames [ 3 ] = { "rtB" , "rtDW" , "NULL_PrevZCX" , } ;
mxArray * ssDW = mxCreateStructMatrix ( 1 , 1 , 3 , ssDWFieldNames ) ;
mr_PIDTrajectory_cacheDataAsMxArray ( ssDW , 0 , 0 , ( const void * ) & ( rtB
) , sizeof ( rtB ) ) ; { static const char * rtdwDataFieldNames [ 6 ] = {
"rtDW.gvmkanlbst" , "rtDW.e0x50jtzxz" , "rtDW.e0ljdij4dh" , "rtDW.aeaptdbisy"
, "rtDW.mhiwfrfp0y" , "rtDW.imsjzof1gf" , } ; mxArray * rtdwData =
mxCreateStructMatrix ( 1 , 1 , 6 , rtdwDataFieldNames ) ;
mr_PIDTrajectory_cacheDataAsMxArray ( rtdwData , 0 , 0 , ( const void * ) & (
rtDW . gvmkanlbst ) , sizeof ( rtDW . gvmkanlbst ) ) ;
mr_PIDTrajectory_cacheDataAsMxArray ( rtdwData , 0 , 1 , ( const void * ) & (
rtDW . e0x50jtzxz ) , sizeof ( rtDW . e0x50jtzxz ) ) ;
mr_PIDTrajectory_cacheDataAsMxArray ( rtdwData , 0 , 2 , ( const void * ) & (
rtDW . e0ljdij4dh ) , sizeof ( rtDW . e0ljdij4dh ) ) ;
mr_PIDTrajectory_cacheDataAsMxArray ( rtdwData , 0 , 3 , ( const void * ) & (
rtDW . aeaptdbisy ) , sizeof ( rtDW . aeaptdbisy ) ) ;
mr_PIDTrajectory_cacheDataAsMxArray ( rtdwData , 0 , 4 , ( const void * ) & (
rtDW . mhiwfrfp0y ) , sizeof ( rtDW . mhiwfrfp0y ) ) ;
mr_PIDTrajectory_cacheDataAsMxArray ( rtdwData , 0 , 5 , ( const void * ) & (
rtDW . imsjzof1gf ) , sizeof ( rtDW . imsjzof1gf ) ) ; mxSetFieldByNumber (
ssDW , 0 , 1 , rtdwData ) ; } return ssDW ; } void mr_PIDTrajectory_SetDWork
( const mxArray * ssDW ) { ( void ) ssDW ;
mr_PIDTrajectory_restoreDataFromMxArray ( ( void * ) & ( rtB ) , ssDW , 0 , 0
, sizeof ( rtB ) ) ; { const mxArray * rtdwData = mxGetFieldByNumber ( ssDW ,
0 , 1 ) ; mr_PIDTrajectory_restoreDataFromMxArray ( ( void * ) & ( rtDW .
gvmkanlbst ) , rtdwData , 0 , 0 , sizeof ( rtDW . gvmkanlbst ) ) ;
mr_PIDTrajectory_restoreDataFromMxArray ( ( void * ) & ( rtDW . e0x50jtzxz )
, rtdwData , 0 , 1 , sizeof ( rtDW . e0x50jtzxz ) ) ;
mr_PIDTrajectory_restoreDataFromMxArray ( ( void * ) & ( rtDW . e0ljdij4dh )
, rtdwData , 0 , 2 , sizeof ( rtDW . e0ljdij4dh ) ) ;
mr_PIDTrajectory_restoreDataFromMxArray ( ( void * ) & ( rtDW . aeaptdbisy )
, rtdwData , 0 , 3 , sizeof ( rtDW . aeaptdbisy ) ) ;
mr_PIDTrajectory_restoreDataFromMxArray ( ( void * ) & ( rtDW . mhiwfrfp0y )
, rtdwData , 0 , 4 , sizeof ( rtDW . mhiwfrfp0y ) ) ;
mr_PIDTrajectory_restoreDataFromMxArray ( ( void * ) & ( rtDW . imsjzof1gf )
, rtdwData , 0 , 5 , sizeof ( rtDW . imsjzof1gf ) ) ; } } mxArray *
mr_PIDTrajectory_GetSimStateDisallowedBlocks ( ) { mxArray * data =
mxCreateCellMatrix ( 1 , 3 ) ; mwIndex subs [ 2 ] , offset ; { static const
char * blockType [ 1 ] = { "Scope" , } ; static const char * blockPath [ 1 ]
= { "PIDTrajectory/Scope" , } ; static const int reason [ 1 ] = { 0 , } ; for
( subs [ 0 ] = 0 ; subs [ 0 ] < 1 ; ++ ( subs [ 0 ] ) ) { subs [ 1 ] = 0 ;
offset = mxCalcSingleSubscript ( data , 2 , subs ) ; mxSetCell ( data ,
offset , mxCreateString ( blockType [ subs [ 0 ] ] ) ) ; subs [ 1 ] = 1 ;
offset = mxCalcSingleSubscript ( data , 2 , subs ) ; mxSetCell ( data ,
offset , mxCreateString ( blockPath [ subs [ 0 ] ] ) ) ; subs [ 1 ] = 2 ;
offset = mxCalcSingleSubscript ( data , 2 , subs ) ; mxSetCell ( data ,
offset , mxCreateDoubleScalar ( ( double ) reason [ subs [ 0 ] ] ) ) ; } }
return data ; } void MdlInitializeSizes ( void ) { ssSetNumContStates ( rtS ,
4 ) ; ssSetNumPeriodicContStates ( rtS , 0 ) ; ssSetNumY ( rtS , 0 ) ;
ssSetNumU ( rtS , 0 ) ; ssSetDirectFeedThrough ( rtS , 0 ) ;
ssSetNumSampleTimes ( rtS , 2 ) ; ssSetNumBlocks ( rtS , 35 ) ;
ssSetNumBlockIO ( rtS , 16 ) ; ssSetNumBlockParams ( rtS , 42095 ) ; } void
MdlInitializeSampleTimes ( void ) { ssSetSampleTime ( rtS , 0 , 0.0 ) ;
ssSetSampleTime ( rtS , 1 , 0.001 ) ; ssSetOffsetTime ( rtS , 0 , 0.0 ) ;
ssSetOffsetTime ( rtS , 1 , 0.0 ) ; } void raccel_set_checksum ( ) {
ssSetChecksumVal ( rtS , 0 , 2081470065U ) ; ssSetChecksumVal ( rtS , 1 ,
3322354520U ) ; ssSetChecksumVal ( rtS , 2 , 2567040593U ) ; ssSetChecksumVal
( rtS , 3 , 303487472U ) ; }
#if defined(_MSC_VER)
#pragma optimize( "", off )
#endif
SimStruct * raccel_register_model ( ssExecutionInfo * executionInfo ) {
static struct _ssMdlInfo mdlInfo ; static struct _ssBlkInfo2 blkInfo2 ;
static struct _ssBlkInfoSLSize blkInfoSLSize ; ( void ) memset ( ( char * )
rtS , 0 , sizeof ( SimStruct ) ) ; ( void ) memset ( ( char * ) & mdlInfo , 0
, sizeof ( struct _ssMdlInfo ) ) ; ( void ) memset ( ( char * ) & blkInfo2 ,
0 , sizeof ( struct _ssBlkInfo2 ) ) ; ( void ) memset ( ( char * ) &
blkInfoSLSize , 0 , sizeof ( struct _ssBlkInfoSLSize ) ) ; ssSetBlkInfo2Ptr (
rtS , & blkInfo2 ) ; ssSetBlkInfoSLSizePtr ( rtS , & blkInfoSLSize ) ;
ssSetMdlInfoPtr ( rtS , & mdlInfo ) ; ssSetExecutionInfo ( rtS ,
executionInfo ) ; slsaAllocOPModelData ( rtS ) ; { static time_T mdlPeriod [
NSAMPLE_TIMES ] ; static time_T mdlOffset [ NSAMPLE_TIMES ] ; static time_T
mdlTaskTimes [ NSAMPLE_TIMES ] ; static int_T mdlTsMap [ NSAMPLE_TIMES ] ;
static int_T mdlSampleHits [ NSAMPLE_TIMES ] ; static boolean_T
mdlTNextWasAdjustedPtr [ NSAMPLE_TIMES ] ; static int_T mdlPerTaskSampleHits
[ NSAMPLE_TIMES * NSAMPLE_TIMES ] ; static time_T mdlTimeOfNextSampleHit [
NSAMPLE_TIMES ] ; { int_T i ; for ( i = 0 ; i < NSAMPLE_TIMES ; i ++ ) {
mdlPeriod [ i ] = 0.0 ; mdlOffset [ i ] = 0.0 ; mdlTaskTimes [ i ] = 0.0 ;
mdlTsMap [ i ] = i ; mdlSampleHits [ i ] = 1 ; } } ssSetSampleTimePtr ( rtS ,
& mdlPeriod [ 0 ] ) ; ssSetOffsetTimePtr ( rtS , & mdlOffset [ 0 ] ) ;
ssSetSampleTimeTaskIDPtr ( rtS , & mdlTsMap [ 0 ] ) ; ssSetTPtr ( rtS , &
mdlTaskTimes [ 0 ] ) ; ssSetSampleHitPtr ( rtS , & mdlSampleHits [ 0 ] ) ;
ssSetTNextWasAdjustedPtr ( rtS , & mdlTNextWasAdjustedPtr [ 0 ] ) ;
ssSetPerTaskSampleHitsPtr ( rtS , & mdlPerTaskSampleHits [ 0 ] ) ;
ssSetTimeOfNextSampleHitPtr ( rtS , & mdlTimeOfNextSampleHit [ 0 ] ) ; }
ssSetSolverMode ( rtS , SOLVER_MODE_SINGLETASKING ) ; { ssSetBlockIO ( rtS ,
( ( void * ) & rtB ) ) ; ( void ) memset ( ( ( void * ) & rtB ) , 0 , sizeof
( B ) ) ; } { real_T * x = ( real_T * ) & rtX ; ssSetContStates ( rtS , x ) ;
( void ) memset ( ( void * ) x , 0 , sizeof ( X ) ) ; } { void * dwork = (
void * ) & rtDW ; ssSetRootDWork ( rtS , dwork ) ; ( void ) memset ( dwork ,
0 , sizeof ( DW ) ) ; } { static DataTypeTransInfo dtInfo ; ( void ) memset (
( char_T * ) & dtInfo , 0 , sizeof ( dtInfo ) ) ; ssSetModelMappingInfo ( rtS
, & dtInfo ) ; dtInfo . numDataTypes = 22 ; dtInfo . dataTypeSizes = &
rtDataTypeSizes [ 0 ] ; dtInfo . dataTypeNames = & rtDataTypeNames [ 0 ] ;
dtInfo . BTransTable = & rtBTransTable ; dtInfo . PTransTable = &
rtPTransTable ; dtInfo . dataTypeInfoTable = rtDataTypeInfoTable ; }
PIDTrajectory_InitializeDataMapInfo ( ) ; ssSetIsRapidAcceleratorActive ( rtS
, true ) ; ssSetRootSS ( rtS , rtS ) ; ssSetVersion ( rtS ,
SIMSTRUCT_VERSION_LEVEL2 ) ; ssSetModelName ( rtS , "PIDTrajectory" ) ;
ssSetPath ( rtS , "PIDTrajectory" ) ; ssSetTStart ( rtS , 0.0 ) ; ssSetTFinal
( rtS , 7.013 ) ; ssSetStepSize ( rtS , 0.001 ) ; ssSetFixedStepSize ( rtS ,
0.001 ) ; { static RTWLogInfo rt_DataLoggingInfo ; rt_DataLoggingInfo .
loggingInterval = ( NULL ) ; ssSetRTWLogInfo ( rtS , & rt_DataLoggingInfo ) ;
} { { static int_T rt_LoggedStateWidths [ ] = { 1 , 1 , 1 , 1 } ; static
int_T rt_LoggedStateNumDimensions [ ] = { 1 , 1 , 1 , 1 } ; static int_T
rt_LoggedStateDimensions [ ] = { 1 , 1 , 1 , 1 } ; static boolean_T
rt_LoggedStateIsVarDims [ ] = { 0 , 0 , 0 , 0 } ; static BuiltInDTypeId
rt_LoggedStateDataTypeIds [ ] = { SS_DOUBLE , SS_DOUBLE , SS_DOUBLE ,
SS_DOUBLE } ; static int_T rt_LoggedStateComplexSignals [ ] = { 0 , 0 , 0 , 0
} ; static RTWPreprocessingFcnPtr rt_LoggingStatePreprocessingFcnPtrs [ ] = {
( NULL ) , ( NULL ) , ( NULL ) , ( NULL ) } ; static const char_T *
rt_LoggedStateLabels [ ] = { "CSTATE" , "CSTATE" , "CSTATE" , "CSTATE" } ;
static const char_T * rt_LoggedStateBlockNames [ ] = {
"PIDTrajectory/Processo/Integrator" , "PIDTrajectory/Processo/Integrator2" ,
"PIDTrajectory/Processo/Integrator1" , "PIDTrajectory/Processo/Integrator3" }
; static const char_T * rt_LoggedStateNames [ ] = { "" , "" , "" , "" } ;
static boolean_T rt_LoggedStateCrossMdlRef [ ] = { 0 , 0 , 0 , 0 } ; static
RTWLogDataTypeConvert rt_RTWLogDataTypeConvert [ ] = { { 0 , SS_DOUBLE ,
SS_DOUBLE , 0 , 0 , 0 , 1.0 , 0 , 0.0 } , { 0 , SS_DOUBLE , SS_DOUBLE , 0 , 0
, 0 , 1.0 , 0 , 0.0 } , { 0 , SS_DOUBLE , SS_DOUBLE , 0 , 0 , 0 , 1.0 , 0 ,
0.0 } , { 0 , SS_DOUBLE , SS_DOUBLE , 0 , 0 , 0 , 1.0 , 0 , 0.0 } } ; static
int_T rt_LoggedStateIdxList [ ] = { 0 , 1 , 2 , 3 } ; static RTWLogSignalInfo
rt_LoggedStateSignalInfo = { 4 , rt_LoggedStateWidths ,
rt_LoggedStateNumDimensions , rt_LoggedStateDimensions ,
rt_LoggedStateIsVarDims , ( NULL ) , ( NULL ) , rt_LoggedStateDataTypeIds ,
rt_LoggedStateComplexSignals , ( NULL ) , rt_LoggingStatePreprocessingFcnPtrs
, { rt_LoggedStateLabels } , ( NULL ) , ( NULL ) , ( NULL ) , {
rt_LoggedStateBlockNames } , { rt_LoggedStateNames } ,
rt_LoggedStateCrossMdlRef , rt_RTWLogDataTypeConvert , rt_LoggedStateIdxList
} ; static void * rt_LoggedStateSignalPtrs [ 4 ] ; rtliSetLogXSignalPtrs (
ssGetRTWLogInfo ( rtS ) , ( LogSignalPtrsType ) rt_LoggedStateSignalPtrs ) ;
rtliSetLogXSignalInfo ( ssGetRTWLogInfo ( rtS ) , & rt_LoggedStateSignalInfo
) ; rt_LoggedStateSignalPtrs [ 0 ] = ( void * ) & rtX . bvq1aeovgd ;
rt_LoggedStateSignalPtrs [ 1 ] = ( void * ) & rtX . pybolimsew ;
rt_LoggedStateSignalPtrs [ 2 ] = ( void * ) & rtX . p405gjuf1q ;
rt_LoggedStateSignalPtrs [ 3 ] = ( void * ) & rtX . cuvdeofxzs ; }
rtliSetLogT ( ssGetRTWLogInfo ( rtS ) , "tout" ) ; rtliSetLogX (
ssGetRTWLogInfo ( rtS ) , "" ) ; rtliSetLogXFinal ( ssGetRTWLogInfo ( rtS ) ,
"xFinal" ) ; rtliSetLogVarNameModifier ( ssGetRTWLogInfo ( rtS ) , "none" ) ;
rtliSetLogFormat ( ssGetRTWLogInfo ( rtS ) , 4 ) ; rtliSetLogMaxRows (
ssGetRTWLogInfo ( rtS ) , 0 ) ; rtliSetLogDecimation ( ssGetRTWLogInfo ( rtS
) , 1 ) ; rtliSetLogY ( ssGetRTWLogInfo ( rtS ) , "" ) ;
rtliSetLogYSignalInfo ( ssGetRTWLogInfo ( rtS ) , ( NULL ) ) ;
rtliSetLogYSignalPtrs ( ssGetRTWLogInfo ( rtS ) , ( NULL ) ) ; } { static
struct _ssStatesInfo2 statesInfo2 ; ssSetStatesInfo2 ( rtS , & statesInfo2 )
; } { static ssPeriodicStatesInfo periodicStatesInfo ;
ssSetPeriodicStatesInfo ( rtS , & periodicStatesInfo ) ; } { static
ssJacobianPerturbationBounds jacobianPerturbationBounds ;
ssSetJacobianPerturbationBounds ( rtS , & jacobianPerturbationBounds ) ; } {
static ssSolverInfo slvrInfo ; static boolean_T contStatesDisabled [ 4 ] ;
ssSetSolverInfo ( rtS , & slvrInfo ) ; ssSetSolverName ( rtS , "ode3" ) ;
ssSetVariableStepSolver ( rtS , 0 ) ; ssSetSolverConsistencyChecking ( rtS ,
0 ) ; ssSetSolverAdaptiveZcDetection ( rtS , 0 ) ;
ssSetSolverRobustResetMethod ( rtS , 0 ) ; ssSetSolverStateProjection ( rtS ,
0 ) ; ssSetSolverMassMatrixType ( rtS , ( ssMatrixType ) 0 ) ;
ssSetSolverMassMatrixNzMax ( rtS , 0 ) ; ssSetModelOutputs ( rtS , MdlOutputs
) ; ssSetModelLogData ( rtS , rt_UpdateTXYLogVars ) ;
ssSetModelLogDataIfInInterval ( rtS , rt_UpdateTXXFYLogVars ) ;
ssSetModelUpdate ( rtS , MdlUpdate ) ; ssSetModelDerivatives ( rtS ,
MdlDerivatives ) ; ssSetTNextTid ( rtS , INT_MIN ) ; ssSetTNext ( rtS ,
rtMinusInf ) ; ssSetSolverNeedsReset ( rtS ) ; ssSetNumNonsampledZCs ( rtS ,
0 ) ; ssSetContStateDisabled ( rtS , contStatesDisabled ) ; }
ssSetChecksumVal ( rtS , 0 , 2081470065U ) ; ssSetChecksumVal ( rtS , 1 ,
3322354520U ) ; ssSetChecksumVal ( rtS , 2 , 2567040593U ) ; ssSetChecksumVal
( rtS , 3 , 303487472U ) ; { static const sysRanDType rtAlwaysEnabled =
SUBSYS_RAN_BC_ENABLE ; static RTWExtModeInfo rt_ExtModeInfo ; static const
sysRanDType * systemRan [ 1 ] ; gblRTWExtModeInfo = & rt_ExtModeInfo ;
ssSetRTWExtModeInfo ( rtS , & rt_ExtModeInfo ) ;
rteiSetSubSystemActiveVectorAddresses ( & rt_ExtModeInfo , systemRan ) ;
systemRan [ 0 ] = & rtAlwaysEnabled ; rteiSetModelMappingInfoPtr (
ssGetRTWExtModeInfo ( rtS ) , & ssGetModelMappingInfo ( rtS ) ) ;
rteiSetChecksumsPtr ( ssGetRTWExtModeInfo ( rtS ) , ssGetChecksums ( rtS ) )
; rteiSetTPtr ( ssGetRTWExtModeInfo ( rtS ) , ssGetTPtr ( rtS ) ) ; }
slsaDisallowedBlocksForSimTargetOP ( rtS ,
mr_PIDTrajectory_GetSimStateDisallowedBlocks ) ; slsaGetWorkFcnForSimTargetOP
( rtS , mr_PIDTrajectory_GetDWork ) ; slsaSetWorkFcnForSimTargetOP ( rtS ,
mr_PIDTrajectory_SetDWork ) ; rt_RapidReadMatFileAndUpdateParams ( rtS ) ; if
( ssGetErrorStatus ( rtS ) ) { return rtS ; } return rtS ; }
#if defined(_MSC_VER)
#pragma optimize( "", on )
#endif
const int_T gblParameterTuningTid = 2 ; void MdlOutputsParameterSampleTime (
int_T tid ) { MdlOutputsTID2 ( tid ) ; }
