#include "rtw_capi.h"
#ifdef HOST_CAPI_BUILD
#include "PIDTrajectory_capi_host.h"
#define sizeof(s) ((size_t)(0xFFFF))
#undef rt_offsetof
#define rt_offsetof(s,el) ((uint16_T)(0xFFFF))
#define TARGET_CONST
#define TARGET_STRING(s) (s)
#ifndef SS_UINT64
#define SS_UINT64 17
#endif
#ifndef SS_INT64
#define SS_INT64 18
#endif
#else
#include "builtin_typeid_types.h"
#include "PIDTrajectory.h"
#include "PIDTrajectory_capi.h"
#include "PIDTrajectory_private.h"
#ifdef LIGHT_WEIGHT_CAPI
#define TARGET_CONST
#define TARGET_STRING(s)               ((NULL))
#else
#define TARGET_CONST                   const
#define TARGET_STRING(s)               (s)
#endif
#endif
static const rtwCAPI_Signals rtBlockSignals [ ] = { { 0 , 0 , TARGET_STRING (
"PIDTrajectory/Sum4" ) , TARGET_STRING ( "ex" ) , 0 , 0 , 0 , 0 , 0 } , { 1 ,
0 , TARGET_STRING ( "PIDTrajectory/Sum5" ) , TARGET_STRING ( "ey" ) , 0 , 0 ,
0 , 0 , 0 } , { 2 , 0 , TARGET_STRING ( "PIDTrajectory/Sum6" ) ,
TARGET_STRING ( "evx" ) , 0 , 0 , 0 , 0 , 0 } , { 3 , 0 , TARGET_STRING (
"PIDTrajectory/Sum7" ) , TARGET_STRING ( "evy" ) , 0 , 0 , 0 , 0 , 0 } , { 4
, 0 , TARGET_STRING ( "PIDTrajectory/Controllore/Sum" ) , TARGET_STRING ( ""
) , 0 , 0 , 0 , 0 , 0 } , { 5 , 0 , TARGET_STRING (
"PIDTrajectory/Controllore/Sum1" ) , TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 0
} , { 6 , 0 , TARGET_STRING ( "PIDTrajectory/Processo/Integrator" ) ,
TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 0 } , { 7 , 0 , TARGET_STRING (
"PIDTrajectory/Processo/Integrator1" ) , TARGET_STRING ( "" ) , 0 , 0 , 0 , 0
, 0 } , { 8 , 0 , TARGET_STRING ( "PIDTrajectory/Processo/Integrator2" ) ,
TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 0 } , { 9 , 0 , TARGET_STRING (
"PIDTrajectory/Processo/Integrator3" ) , TARGET_STRING ( "" ) , 0 , 0 , 0 , 0
, 0 } , { 10 , 0 , TARGET_STRING (
"PIDTrajectory/Controllore/Signal From Workspace4/From Workspace" ) ,
TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 1 } , { 11 , 0 , TARGET_STRING (
"PIDTrajectory/Controllore/Signal From Workspace5/From Workspace" ) ,
TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 1 } , { 12 , 0 , TARGET_STRING (
"PIDTrajectory/Riferimenti/Signal From Workspace/From Workspace" ) ,
TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 1 } , { 13 , 0 , TARGET_STRING (
"PIDTrajectory/Riferimenti/Signal From Workspace1/From Workspace" ) ,
TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 1 } , { 14 , 0 , TARGET_STRING (
"PIDTrajectory/Riferimenti/Signal From Workspace2/From Workspace" ) ,
TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 1 } , { 15 , 0 , TARGET_STRING (
"PIDTrajectory/Riferimenti/Signal From Workspace3/From Workspace" ) ,
TARGET_STRING ( "" ) , 0 , 0 , 0 , 0 , 1 } , { 0 , 0 , ( NULL ) , ( NULL ) ,
0 , 0 , 0 , 0 , 0 } } ; static const rtwCAPI_BlockParameters
rtBlockParameters [ ] = { { 16 , TARGET_STRING (
"PIDTrajectory/Controllore/PIDX/Gain1" ) , TARGET_STRING ( "Gain" ) , 0 , 0 ,
0 } , { 17 , TARGET_STRING ( "PIDTrajectory/Controllore/PIDY/Gain1" ) ,
TARGET_STRING ( "Gain" ) , 0 , 0 , 0 } , { 18 , TARGET_STRING (
"PIDTrajectory/Controllore/Signal From Workspace4/From Workspace" ) ,
TARGET_STRING ( "Data0" ) , 0 , 1 , 0 } , { 19 , TARGET_STRING (
"PIDTrajectory/Controllore/Signal From Workspace5/From Workspace" ) ,
TARGET_STRING ( "Data0" ) , 0 , 1 , 0 } , { 20 , TARGET_STRING (
"PIDTrajectory/Riferimenti/Signal From Workspace/From Workspace" ) ,
TARGET_STRING ( "Data0" ) , 0 , 2 , 0 } , { 21 , TARGET_STRING (
"PIDTrajectory/Riferimenti/Signal From Workspace1/From Workspace" ) ,
TARGET_STRING ( "Data0" ) , 0 , 2 , 0 } , { 22 , TARGET_STRING (
"PIDTrajectory/Riferimenti/Signal From Workspace2/From Workspace" ) ,
TARGET_STRING ( "Data0" ) , 0 , 1 , 0 } , { 23 , TARGET_STRING (
"PIDTrajectory/Riferimenti/Signal From Workspace3/From Workspace" ) ,
TARGET_STRING ( "Data0" ) , 0 , 1 , 0 } , { 0 , ( NULL ) , ( NULL ) , 0 , 0 ,
0 } } ; static int_T rt_LoggedStateIdxList [ ] = { - 1 } ; static const
rtwCAPI_Signals rtRootInputs [ ] = { { 0 , 0 , ( NULL ) , ( NULL ) , 0 , 0 ,
0 , 0 , 0 } } ; static const rtwCAPI_Signals rtRootOutputs [ ] = { { 0 , 0 ,
( NULL ) , ( NULL ) , 0 , 0 , 0 , 0 , 0 } } ; static const
rtwCAPI_ModelParameters rtModelParameters [ ] = { { 24 , TARGET_STRING ( "Fa"
) , 0 , 0 , 0 } , { 25 , TARGET_STRING ( "Kd" ) , 0 , 0 , 0 } , { 26 ,
TARGET_STRING ( "Kp" ) , 0 , 0 , 0 } , { 27 , TARGET_STRING ( "v0x" ) , 0 , 0
, 0 } , { 28 , TARGET_STRING ( "v0y" ) , 0 , 0 , 0 } , { 29 , TARGET_STRING (
"x0" ) , 0 , 0 , 0 } , { 30 , TARGET_STRING ( "y0" ) , 0 , 0 , 0 } , { 0 , (
NULL ) , 0 , 0 , 0 } } ;
#ifndef HOST_CAPI_BUILD
static void * rtDataAddrMap [ ] = { & rtB . fts1qnwwnm , & rtB . bqqkaib5ws ,
& rtB . dn3s1knom1 , & rtB . fukvwwoumx , & rtB . ekmphufo03 , & rtB .
mpinfjn4a5 , & rtB . dzhv4gakca , & rtB . ebdsuq3ovc , & rtB . ayn2l5kx4f , &
rtB . of4hv5yd2x , & rtB . ceh1qr02vf , & rtB . fo23lqib5q , & rtB .
lqgo1ctcu0 , & rtB . bxc5wbeagz , & rtB . bcl5o0t22v , & rtB . erdbg52csk , &
rtP . Gain1_Gain , & rtP . Gain1_Gain_ipqblw4zv4 , & rtP .
FromWorkspace_Data0_irypaqsgbf [ 0 ] , & rtP . FromWorkspace_Data0_a2gi03qt2b
[ 0 ] , & rtP . FromWorkspace_Data0 [ 0 ] , & rtP .
FromWorkspace_Data0_fbwrwi4aj1 [ 0 ] , & rtP . FromWorkspace_Data0_erdsjhbktd
[ 0 ] , & rtP . FromWorkspace_Data0_elsudc4efs [ 0 ] , & rtP . Fa , & rtP .
Kd , & rtP . Kp , & rtP . v0x , & rtP . v0y , & rtP . x0 , & rtP . y0 , } ;
static int32_T * rtVarDimsAddrMap [ ] = { ( NULL ) } ;
#endif
static TARGET_CONST rtwCAPI_DataTypeMap rtDataTypeMap [ ] = { { "double" ,
"real_T" , 0 , 0 , sizeof ( real_T ) , ( uint8_T ) SS_DOUBLE , 0 , 0 , 0 } }
;
#ifdef HOST_CAPI_BUILD
#undef sizeof
#endif
static TARGET_CONST rtwCAPI_ElementMap rtElementMap [ ] = { { ( NULL ) , 0 ,
0 , 0 , 0 } , } ; static const rtwCAPI_DimensionMap rtDimensionMap [ ] = { {
rtwCAPI_SCALAR , 0 , 2 , 0 } , { rtwCAPI_VECTOR , 2 , 2 , 0 } , {
rtwCAPI_VECTOR , 4 , 2 , 0 } } ; static const uint_T rtDimensionArray [ ] = {
1 , 1 , 3006 , 1 , 3007 , 1 } ; static const real_T rtcapiStoredFloats [ ] =
{ 0.0 , 0.001 } ; static const rtwCAPI_FixPtMap rtFixPtMap [ ] = { { ( NULL )
, ( NULL ) , rtwCAPI_FIX_RESERVED , 0 , 0 , ( boolean_T ) 0 } , } ; static
const rtwCAPI_SampleTimeMap rtSampleTimeMap [ ] = { { ( const void * ) &
rtcapiStoredFloats [ 0 ] , ( const void * ) & rtcapiStoredFloats [ 0 ] , (
int8_T ) 0 , ( uint8_T ) 0 } , { ( const void * ) & rtcapiStoredFloats [ 1 ]
, ( const void * ) & rtcapiStoredFloats [ 0 ] , ( int8_T ) 1 , ( uint8_T ) 0
} } ; static rtwCAPI_ModelMappingStaticInfo mmiStatic = { { rtBlockSignals ,
16 , rtRootInputs , 0 , rtRootOutputs , 0 } , { rtBlockParameters , 8 ,
rtModelParameters , 7 } , { ( NULL ) , 0 } , { rtDataTypeMap , rtDimensionMap
, rtFixPtMap , rtElementMap , rtSampleTimeMap , rtDimensionArray } , "float"
, { 872953965U , 1838436352U , 1664098222U , 2965280412U } , ( NULL ) , 0 , (
boolean_T ) 0 , rt_LoggedStateIdxList } ; const
rtwCAPI_ModelMappingStaticInfo * PIDTrajectory_GetCAPIStaticMap ( void ) {
return & mmiStatic ; }
#ifndef HOST_CAPI_BUILD
void PIDTrajectory_InitializeDataMapInfo ( void ) { rtwCAPI_SetVersion ( ( *
rt_dataMapInfoPtr ) . mmi , 1 ) ; rtwCAPI_SetStaticMap ( ( *
rt_dataMapInfoPtr ) . mmi , & mmiStatic ) ; rtwCAPI_SetLoggingStaticMap ( ( *
rt_dataMapInfoPtr ) . mmi , ( NULL ) ) ; rtwCAPI_SetDataAddressMap ( ( *
rt_dataMapInfoPtr ) . mmi , rtDataAddrMap ) ; rtwCAPI_SetVarDimsAddressMap (
( * rt_dataMapInfoPtr ) . mmi , rtVarDimsAddrMap ) ;
rtwCAPI_SetInstanceLoggingInfo ( ( * rt_dataMapInfoPtr ) . mmi , ( NULL ) ) ;
rtwCAPI_SetChildMMIArray ( ( * rt_dataMapInfoPtr ) . mmi , ( NULL ) ) ;
rtwCAPI_SetChildMMIArrayLen ( ( * rt_dataMapInfoPtr ) . mmi , 0 ) ; }
#else
#ifdef __cplusplus
extern "C" {
#endif
void PIDTrajectory_host_InitializeDataMapInfo (
PIDTrajectory_host_DataMapInfo_T * dataMap , const char * path ) {
rtwCAPI_SetVersion ( dataMap -> mmi , 1 ) ; rtwCAPI_SetStaticMap ( dataMap ->
mmi , & mmiStatic ) ; rtwCAPI_SetDataAddressMap ( dataMap -> mmi , ( NULL ) )
; rtwCAPI_SetVarDimsAddressMap ( dataMap -> mmi , ( NULL ) ) ;
rtwCAPI_SetPath ( dataMap -> mmi , path ) ; rtwCAPI_SetFullPath ( dataMap ->
mmi , ( NULL ) ) ; rtwCAPI_SetChildMMIArray ( dataMap -> mmi , ( NULL ) ) ;
rtwCAPI_SetChildMMIArrayLen ( dataMap -> mmi , 0 ) ; }
#ifdef __cplusplus
}
#endif
#endif
