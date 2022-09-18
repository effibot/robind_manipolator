#ifndef RTW_HEADER_PIDTrajectory_h_
#define RTW_HEADER_PIDTrajectory_h_
#ifndef PIDTrajectory_COMMON_INCLUDES_
#define PIDTrajectory_COMMON_INCLUDES_
#include <stdlib.h>
#include "sl_AsyncioQueue/AsyncioQueueCAPI.h"
#include "rtwtypes.h"
#include "sigstream_rtw.h"
#include "simtarget/slSimTgtSigstreamRTW.h"
#include "simtarget/slSimTgtSlioCoreRTW.h"
#include "simtarget/slSimTgtSlioClientsRTW.h"
#include "simtarget/slSimTgtSlioSdiRTW.h"
#include "simstruc.h"
#include "fixedpoint.h"
#include "raccel.h"
#include "slsv_diagnostic_codegen_c_api.h"
#include "rt_logging_simtarget.h"
#include "dt_info.h"
#include "ext_work.h"
#endif
#include "PIDTrajectory_types.h"
#include "mwmathutil.h"
#include <stddef.h>
#include "rtw_modelmap_simtarget.h"
#include "rt_defines.h"
#include <string.h>
#include "rtGetInf.h"
#include "rt_nonfinite.h"
#define MODEL_NAME PIDTrajectory
#define NSAMPLE_TIMES (3) 
#define NINPUTS (0)       
#define NOUTPUTS (0)     
#define NBLOCKIO (16) 
#define NUM_ZC_EVENTS (0) 
#ifndef NCSTATES
#define NCSTATES (4)   
#elif NCSTATES != 4
#error Invalid specification of NCSTATES defined in compiler command
#endif
#ifndef rtmGetDataMapInfo
#define rtmGetDataMapInfo(rtm) (*rt_dataMapInfoPtr)
#endif
#ifndef rtmSetDataMapInfo
#define rtmSetDataMapInfo(rtm, val) (rt_dataMapInfoPtr = &val)
#endif
#ifndef IN_RACCEL_MAIN
#endif
typedef struct { real_T dzhv4gakca ; real_T ayn2l5kx4f ; real_T ebdsuq3ovc ;
real_T of4hv5yd2x ; real_T lqgo1ctcu0 ; real_T fts1qnwwnm ; real_T bcl5o0t22v
; real_T dn3s1knom1 ; real_T ceh1qr02vf ; real_T ekmphufo03 ; real_T
bxc5wbeagz ; real_T bqqkaib5ws ; real_T erdbg52csk ; real_T fukvwwoumx ;
real_T fo23lqib5q ; real_T mpinfjn4a5 ; } B ; typedef struct { struct { void
* LoggedData [ 2 ] ; } a53cvwyjia ; struct { void * AQHandles ; } ecofnqvfjg
; struct { void * AQHandles ; } eiskowcg51 ; struct { void * TimePtr ; void *
DataPtr ; void * RSimInfoPtr ; } e1dw0gdlsu ; struct { void * TimePtr ; void
* DataPtr ; void * RSimInfoPtr ; } loho3tdcc3 ; struct { void * TimePtr ;
void * DataPtr ; void * RSimInfoPtr ; } b1pf4c5bw1 ; struct { void * TimePtr
; void * DataPtr ; void * RSimInfoPtr ; } ejxqv5alzd ; struct { void *
TimePtr ; void * DataPtr ; void * RSimInfoPtr ; } d4sbxigd01 ; struct { void
* TimePtr ; void * DataPtr ; void * RSimInfoPtr ; } pc2nyef1qt ; struct {
void * AQHandles ; } lseu3eofyv ; struct { void * AQHandles ; } dbtjzghbsv ;
struct { int_T PrevIndex ; } gvmkanlbst ; struct { int_T PrevIndex ; }
e0x50jtzxz ; struct { int_T PrevIndex ; } e0ljdij4dh ; struct { int_T
PrevIndex ; } aeaptdbisy ; struct { int_T PrevIndex ; } mhiwfrfp0y ; struct {
int_T PrevIndex ; } imsjzof1gf ; } DW ; typedef struct { real_T bvq1aeovgd ;
real_T pybolimsew ; real_T p405gjuf1q ; real_T cuvdeofxzs ; } X ; typedef
struct { real_T bvq1aeovgd ; real_T pybolimsew ; real_T p405gjuf1q ; real_T
cuvdeofxzs ; } XDot ; typedef struct { boolean_T bvq1aeovgd ; boolean_T
pybolimsew ; boolean_T p405gjuf1q ; boolean_T cuvdeofxzs ; } XDis ; typedef
struct { rtwCAPI_ModelMappingInfo mmi ; } DataMapInfo ; struct P_ { real_T Fa
; real_T Kd ; real_T Kp ; real_T v0x ; real_T v0y ; real_T x0 ; real_T y0 ;
real_T FromWorkspace_Data0 [ 2005 ] ; real_T FromWorkspace_Data0_erdsjhbktd [
2004 ] ; real_T Gain1_Gain ; real_T FromWorkspace_Data0_irypaqsgbf [ 2004 ] ;
real_T FromWorkspace_Data0_fbwrwi4aj1 [ 2005 ] ; real_T
FromWorkspace_Data0_elsudc4efs [ 2004 ] ; real_T Gain1_Gain_ipqblw4zv4 ;
real_T FromWorkspace_Data0_a2gi03qt2b [ 2004 ] ; } ; extern const char *
RT_MEMORY_ALLOCATION_ERROR ; extern B rtB ; extern X rtX ; extern DW rtDW ;
extern P rtP ; extern mxArray * mr_PIDTrajectory_GetDWork ( ) ; extern void
mr_PIDTrajectory_SetDWork ( const mxArray * ssDW ) ; extern mxArray *
mr_PIDTrajectory_GetSimStateDisallowedBlocks ( ) ; extern const
rtwCAPI_ModelMappingStaticInfo * PIDTrajectory_GetCAPIStaticMap ( void ) ;
extern SimStruct * const rtS ; extern const int_T gblNumToFiles ; extern
const int_T gblNumFrFiles ; extern const int_T gblNumFrWksBlocks ; extern
rtInportTUtable * gblInportTUtables ; extern const char * gblInportFileName ;
extern const int_T gblNumRootInportBlks ; extern const int_T
gblNumModelInputs ; extern const int_T gblInportDataTypeIdx [ ] ; extern
const int_T gblInportDims [ ] ; extern const int_T gblInportComplex [ ] ;
extern const int_T gblInportInterpoFlag [ ] ; extern const int_T
gblInportContinuous [ ] ; extern const int_T gblParameterTuningTid ; extern
DataMapInfo * rt_dataMapInfoPtr ; extern rtwCAPI_ModelMappingInfo *
rt_modelMapInfoPtr ; void MdlOutputs ( int_T tid ) ; void
MdlOutputsParameterSampleTime ( int_T tid ) ; void MdlUpdate ( int_T tid ) ;
void MdlTerminate ( void ) ; void MdlInitializeSizes ( void ) ; void
MdlInitializeSampleTimes ( void ) ; SimStruct * raccel_register_model (
ssExecutionInfo * executionInfo ) ;
#endif
