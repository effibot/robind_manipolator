//package com.effibot.robind_manipolator;/* inversa di posizione */
//// posizione del polso
//private float xdes, ydes, zdes = 0;
//private float roll, pitch, yaw = 0;
//private float xh = xdes - d6*cos(roll)*sin(pitch);
//private float yh = ydes - d6*sin(roll)*cos(pitch);
//private float zh = - d6*cos(pitch);
//private int elbow = 1;
//// setups for calculations
//private double A = elbow*sqrt(pow(xh,2)+pow(yh,2));
//private double B = zh-d1;
//private double C = a2-d4*s3
//private double D = d4*c3;
//private double det = pow(C,2)+pow(D,2);
//// sin(q[3])
//private double ds3 = 2*a2*d4;
//private double ns3 = pow(B,2)+pow(yh,2)+pow(xh,2)-
//                        pow(a2,2)-pow(d4,2);
//private double s3 = -(ns3)/ds3;
//// cos(q[3])
//private double c3 = elbow*sqrt(1-pow(s3,2));
//// q[3]
//private double q3 = atan2(s3,q3);
//// cos(q[2])
//private double c2 = (D*B+A*C)/det;
//// sin(q[2])
//private double s2 = (-D*A+C*B)/det;
//// q[2]
//private double q2 = atan2(s2,c2);
//// another setup
//private double k = a2*c2-d4*sin(q2+q3);
//// cos(q[1])
//private double c1 = xh/k;
//// sin(q[1])
//private double s1 = yh/k;
//// q[1]
//private double q1 = atan2(s1,c1);
///* inversa di orientamento */
//// q[5]
//private double c5 = R[3,3];
//private double s5 = elbow*sqrt(1-pow(c5,2));
//private double q5 = atan2(s5,c5);
//// q[4]
//private double c4 = -R[1,3]/s5;
//private double s4 = -R[2,3]/s5;
//private double q4 = atan2(s4,c4);
//// q[6]
//private double c6 = R[3,1]/s5;
//private double s6 = -R[3,2]/s5;
//private double q6 = atan2(s6,c6);
//
//
//
///// controllare gli avvitamenti sinistri
//
//
//
//
//
//
//
