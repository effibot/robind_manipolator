<TeXmacs|2.1.1>

<style|<tuple|generic|italian|maxima>>

<\body>
  <\session|maxima|default>
    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>1) >
    <|unfolded-io>
      tab:matrix([q[1],D[1],-%pi/2,0],[q[2],0,0,A[2]],[q[3],0,%pi/2,0],

      \ \ \ \ \ \ \ \ \ \ \ [q[4],D[4],-%pi/2,0],[q[5],0,%pi/2,0],[q[6],D[6],0,0])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o1>)
      >><matrix|<tformat|<table|<row|<cell|q<rsub|1>>|<cell|D<rsub|1>>|<cell|-<frac|\<pi\>|2>>|<cell|0>>|<row|<cell|q<rsub|2>>|<cell|0>|<cell|0>|<cell|A<rsub|2>>>|<row|<cell|q<rsub|3>>|<cell|0>|<cell|<frac|\<pi\>|2>>|<cell|0>>|<row|<cell|q<rsub|4>>|<cell|D<rsub|4>>|<cell|-<frac|\<pi\>|2>>|<cell|0>>|<row|<cell|q<rsub|5>>|<cell|0>|<cell|<frac|\<pi\>|2>>|<cell|0>>|<row|<cell|q<rsub|6>>|<cell|D<rsub|6>>|<cell|0>|<cell|0>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>3) >
    <|unfolded-io>
      D[1]:33
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o3>)
      >>33>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>4) >
    <|unfolded-io>
      A[2]:50
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o4>)
      >>50>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>5) >
    <|unfolded-io>
      D[4]:51
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o5>)
      >>51>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>6) >
    <|unfolded-io>
      D[6]:12
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o6>)
      >>12>>
    </unfolded-io>

    <\input>
      <with|color|red|(<with|math-font-family|rm|%i>7) >
    <|input>
      \;
    </input>

    <\input>
      <with|color|red|(<with|math-font-family|rm|%i>46) >
    <|input>
      \;
    </input>

    <\input>
      <with|color|red|(<with|math-font-family|rm|%i>46) >
    <|input>
      \;
    </input>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>7) >
    <|unfolded-io>
      tab:matrix([0,D[1],-%pi/2,0],[-%pi/2,0,0,A[2]],[%pi/2,0,%pi/2,0],

      \ \ \ \ \ \ \ \ \ \ \ [0,D[4],-%pi/2,0],[0,0,%pi/2,0],[0,D[6],0,0])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o7>)
      >><matrix|<tformat|<table|<row|<cell|0>|<cell|33>|<cell|-<frac|\<pi\>|2>>|<cell|0>>|<row|<cell|-<frac|\<pi\>|2>>|<cell|0>|<cell|0>|<cell|50>>|<row|<cell|<frac|\<pi\>|2>>|<cell|0>|<cell|<frac|\<pi\>|2>>|<cell|0>>|<row|<cell|0>|<cell|51>|<cell|-<frac|\<pi\>|2>>|<cell|0>>|<row|<cell|0>|<cell|0>|<cell|<frac|\<pi\>|2>>|<cell|0>>|<row|<cell|0>|<cell|12>|<cell|0>|<cell|0>>>>>>>
    </unfolded-io>

    <\input>
      <with|color|red|(<with|math-font-family|rm|%i>8) >
    <|input>
      dh:DH(tab)[1]$
    </input>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>9) >
    <|unfolded-io>
      Q36:DH(submatrix(1,2,3,tab))[1]
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o9>)
      >><matrix|<tformat|<table|<row|<cell|1>|<cell|0>|<cell|0>|<cell|0>>|<row|<cell|0>|<cell|1>|<cell|0>|<cell|0>>|<row|<cell|0>|<cell|0>|<cell|1>|<cell|63>>|<row|<cell|0>|<cell|0>|<cell|0>|<cell|1>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>4) >
    <|unfolded-io>
      R36:submatrix(4,Q36,4)
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o4>)
      >><matrix|<tformat|<table|<row|<cell|cos <around*|(|q<rsub|4>|)>*cos
      <around*|(|q<rsub|5>|)>*cos <around*|(|q<rsub|6>|)>-sin
      <around*|(|q<rsub|4>|)>*sin <around*|(|q<rsub|6>|)>>|<cell|-cos
      <around*|(|q<rsub|4>|)>*cos <around*|(|q<rsub|5>|)>*sin
      <around*|(|q<rsub|6>|)>-sin <around*|(|q<rsub|4>|)>*cos
      <around*|(|q<rsub|6>|)>>|<cell|cos <around*|(|q<rsub|4>|)>*sin
      <around*|(|q<rsub|5>|)>>>|<row|<cell|cos <around*|(|q<rsub|4>|)>*sin
      <around*|(|q<rsub|6>|)>+sin <around*|(|q<rsub|4>|)>*cos
      <around*|(|q<rsub|5>|)>*cos <around*|(|q<rsub|6>|)>>|<cell|cos
      <around*|(|q<rsub|4>|)>*cos <around*|(|q<rsub|6>|)>-sin
      <around*|(|q<rsub|4>|)>*cos <around*|(|q<rsub|5>|)>*sin
      <around*|(|q<rsub|6>|)>>|<cell|sin <around*|(|q<rsub|4>|)>*sin
      <around*|(|q<rsub|5>|)>>>|<row|<cell|-sin <around*|(|q<rsub|5>|)>*cos
      <around*|(|q<rsub|6>|)>>|<cell|sin <around*|(|q<rsub|5>|)>*sin
      <around*|(|q<rsub|6>|)>>|<cell|cos <around*|(|q<rsub|5>|)>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>5) >
    <|unfolded-io>
      Q03:DH(submatrix(4,5,6,tab))[1]
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o5>)
      >><matrix|<tformat|<table|<row|<cell|cos <around*|(|q<rsub|1>|)>*cos
      <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|-sin
      <around*|(|q<rsub|1>|)>>|<cell|cos <around*|(|q<rsub|1>|)>*sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|A<rsub|2>*cos
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>|<row|<cell|sin
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|cos
      <around*|(|q<rsub|1>|)>>|<cell|sin <around*|(|q<rsub|1>|)>*sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|A<rsub|2>*sin
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>|<row|<cell|-sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|0>|<cell|cos
      <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|D<rsub|1>-A<rsub|2>*sin
      <around*|(|q<rsub|2>|)>>>|<row|<cell|0>|<cell|0>|<cell|0>|<cell|1>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>6) >
    <|unfolded-io>
      d36:submatrix(4,Q36,1,2,3)
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o6>)
      >><matrix|<tformat|<table|<row|<cell|D<rsub|6>*cos
      <around*|(|q<rsub|4>|)>*sin <around*|(|q<rsub|5>|)>>>|<row|<cell|D<rsub|6>*sin
      <around*|(|q<rsub|4>|)>*sin <around*|(|q<rsub|5>|)>>>|<row|<cell|D<rsub|6>*cos
      <around*|(|q<rsub|5>|)>+D<rsub|4>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>7) >
    <|unfolded-io>
      d1:matrix([0],[0],[D[6]])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o7>)
      >><matrix|<tformat|<table|<row|<cell|0>>|<row|<cell|0>>|<row|<cell|D<rsub|6>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>8) >
    <|unfolded-io>
      d0:matrix([0],[0],[D[4]])
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o8>)
      >><matrix|<tformat|<table|<row|<cell|0>>|<row|<cell|0>>|<row|<cell|D<rsub|4>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>9) >
    <|unfolded-io>
      R:rot3(z,alpha).rot3(y,beta).rot3(z,gamma)
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o9>)
      >><matrix|<tformat|<table|<row|<cell|cos <around*|(|\<alpha\>|)>*cos
      <around*|(|\<beta\>|)>*cos <around*|(|\<gamma\>|)>-sin
      <around*|(|\<alpha\>|)>*sin <around*|(|\<gamma\>|)>>|<cell|-cos
      <around*|(|\<alpha\>|)>*cos <around*|(|\<beta\>|)>*sin
      <around*|(|\<gamma\>|)>-sin <around*|(|\<alpha\>|)>*cos
      <around*|(|\<gamma\>|)>>|<cell|cos <around*|(|\<alpha\>|)>*sin
      <around*|(|\<beta\>|)>>>|<row|<cell|cos <around*|(|\<alpha\>|)>*sin
      <around*|(|\<gamma\>|)>+sin <around*|(|\<alpha\>|)>*cos
      <around*|(|\<beta\>|)>*cos <around*|(|\<gamma\>|)>>|<cell|cos
      <around*|(|\<alpha\>|)>*cos <around*|(|\<gamma\>|)>-sin
      <around*|(|\<alpha\>|)>*cos <around*|(|\<beta\>|)>*sin
      <around*|(|\<gamma\>|)>>|<cell|sin <around*|(|\<alpha\>|)>*sin
      <around*|(|\<beta\>|)>>>|<row|<cell|-sin <around*|(|\<beta\>|)>*cos
      <around*|(|\<gamma\>|)>>|<cell|sin <around*|(|\<beta\>|)>*sin
      <around*|(|\<gamma\>|)>>|<cell|cos <around*|(|\<beta\>|)>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>10) >
    <|unfolded-io>
      P:matrix([x],[y],[z])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o10>)
      >><matrix|<tformat|<table|<row|<cell|x>>|<row|<cell|y>>|<row|<cell|z>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>11) >
    <|unfolded-io>
      Lhs:P-R.d1
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o11>)
      >><matrix|<tformat|<table|<row|<cell|x-D<rsub|6>*cos
      <around*|(|\<alpha\>|)>*sin <around*|(|\<beta\>|)>>>|<row|<cell|y-D<rsub|6>*sin
      <around*|(|\<alpha\>|)>*sin <around*|(|\<beta\>|)>>>|<row|<cell|z-D<rsub|6>*cos
      <around*|(|\<beta\>|)>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>12) >
    <|unfolded-io>
      Ph:matrix([x[h]],[y[h]],[z[h]])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o12>)
      >><matrix|<tformat|<table|<row|<cell|x<rsub|h>>>|<row|<cell|y<rsub|h>>>|<row|<cell|z<rsub|h>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>13) >
    <|unfolded-io>
      R03:submatrix(4,Q03,4)
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o13>)
      >><matrix|<tformat|<table|<row|<cell|cos <around*|(|q<rsub|1>|)>*cos
      <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|-sin
      <around*|(|q<rsub|1>|)>>|<cell|cos <around*|(|q<rsub|1>|)>*sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>>>|<row|<cell|sin
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|cos
      <around*|(|q<rsub|1>|)>>|<cell|sin <around*|(|q<rsub|1>|)>*sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>>>|<row|<cell|-sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>>|<cell|0>|<cell|cos
      <around*|(|q<rsub|3>+q<rsub|2>|)>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>14) >
    <|unfolded-io>
      d03:submatrix(4,Q03,1,2,3)
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o14>)
      >><matrix|<tformat|<table|<row|<cell|A<rsub|2>*cos
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>|<row|<cell|A<rsub|2>*sin
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>|<row|<cell|D<rsub|1>-A<rsub|2>*sin
      <around*|(|q<rsub|2>|)>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>15) >
    <|unfolded-io>
      Rhs:R03.d0+d03
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o15>)
      >><matrix|<tformat|<table|<row|<cell|D<rsub|4>*cos
      <around*|(|q<rsub|1>|)>*sin <around*|(|q<rsub|3>+q<rsub|2>|)>+A<rsub|2>*cos
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>|<row|<cell|D<rsub|4>*sin
      <around*|(|q<rsub|1>|)>*sin <around*|(|q<rsub|3>+q<rsub|2>|)>+A<rsub|2>*sin
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>|<row|<cell|D<rsub|4>*cos
      <around*|(|q<rsub|3>+q<rsub|2>|)>-A<rsub|2>*sin
      <around*|(|q<rsub|2>|)>+D<rsub|1>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>16) >
    <|unfolded-io>
      eq1:Ph[1,1]=Rhs[1,1]
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o16>)
      >>x<rsub|h>=D<rsub|4>*cos <around*|(|q<rsub|1>|)>*sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>+A<rsub|2>*cos
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>17) >
    <|unfolded-io>
      eq2:Ph[2,1]=Rhs[2,1]
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o17>)
      >>y<rsub|h>=D<rsub|4>*sin <around*|(|q<rsub|1>|)>*sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>+A<rsub|2>*sin
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>18) >
    <|unfolded-io>
      eq3:Ph[3,1]=Rhs[3,1]
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o18>)
      >>z<rsub|h>=D<rsub|4>*cos <around*|(|q<rsub|3>+q<rsub|2>|)>-A<rsub|2>*sin
      <around*|(|q<rsub|2>|)>+D<rsub|1>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>19) >
    <|unfolded-io>
      eqq1:factor(trigexpand(trigsimp(eq1^2+eq2^2)))
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o19>)
      >>y<rsub|h><rsup|2>+x<rsub|h><rsup|2>=<around*|(|D<rsub|4>*cos
      <around*|(|q<rsub|2>|)>*sin <around*|(|q<rsub|3>|)>+D<rsub|4>*sin
      <around*|(|q<rsub|2>|)>*cos <around*|(|q<rsub|3>|)>+A<rsub|2>*cos
      <around*|(|q<rsub|2>|)>|)><rsup|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>20) >
    <|unfolded-io>
      eqq2:(trigexpand(eq3-D[1]))^2
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o20>)
      >><around*|(|z<rsub|h>-D<rsub|1>|)><rsup|2>=<around*|(|D<rsub|4>*<around*|(|cos
      <around*|(|q<rsub|2>|)>*cos <around*|(|q<rsub|3>|)>-sin
      <around*|(|q<rsub|2>|)>*sin <around*|(|q<rsub|3>|)>|)>-A<rsub|2>*sin
      <around*|(|q<rsub|2>|)>|)><rsup|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>21) >
    <|unfolded-io>
      eqq3:factor(factor(trigsimp(expand(eqq1+eqq2)))-y[h]^2-x[h]^2)
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o21>)
      >><around*|(|z<rsub|h>-D<rsub|1>|)><rsup|2>=-<around*|(|y<rsub|h><rsup|2>+x<rsub|h><rsup|2>-2*A<rsub|2>*D<rsub|4>*sin
      <around*|(|q<rsub|3>|)>-D<rsub|4><rsup|2>-A<rsub|2><rsup|2>|)>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>22) >
    <|unfolded-io>
      l:lhs(eqq3)+y[h]^2+x[h]^2
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o22>)
      >><around*|(|z<rsub|h>-D<rsub|1>|)><rsup|2>+y<rsub|h><rsup|2>+x<rsub|h><rsup|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>23) >
    <|unfolded-io>
      r:expand(rhs(eqq3))+y[h]^2+x[h]^2
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o23>)
      >>2*A<rsub|2>*D<rsub|4>*sin <around*|(|q<rsub|3>|)>+D<rsub|4><rsup|2>+A<rsub|2><rsup|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>24) >
    <|unfolded-io>
      eqq4:l=r
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o24>)
      >><around*|(|z<rsub|h>-D<rsub|1>|)><rsup|2>+y<rsub|h><rsup|2>+x<rsub|h><rsup|2>=2*A<rsub|2>*D<rsub|4>*sin
      <around*|(|q<rsub|3>|)>+D<rsub|4><rsup|2>+A<rsub|2><rsup|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>25) >
    <|unfolded-io>
      let(sin(q[3]),s[3])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o25>)
      >>sin <around*|(|q<rsub|3>|)>\<longrightarrow\>s<rsub|3>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>26) >
    <|unfolded-io>
      eqq4:letsimp(eqq4)
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o26>)
      >>x<rsub|h><rsup|2>+y<rsub|h><rsup|2>+<around*|(|z<rsub|h>-D<rsub|1>|)><rsup|2>=A<rsub|2><rsup|2>+D<rsub|4><rsup|2>+2*A<rsub|2>*s<rsub|3>*D<rsub|4>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>27) >
    <|unfolded-io>
      factor(solve(eqq4,s[3])[1])
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o27>)
      >>s<rsub|3>=<frac|z<rsub|h><rsup|2>-2*D<rsub|1>*z<rsub|h>+y<rsub|h><rsup|2>+x<rsub|h><rsup|2>-D<rsub|4><rsup|2>-A<rsub|2><rsup|2>+D<rsub|1><rsup|2>|2*A<rsub|2>*D<rsub|4>>>>
    </unfolded-io>

    <\input>
      <with|color|red|(<with|math-font-family|rm|%i>29) >
    <|input>
      \;
    </input>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>28) >
    <|unfolded-io>
      let(cos(q[3]),c[3])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o28>)
      >>cos <around*|(|q<rsub|3>|)>\<longrightarrow\>c<rsub|3>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>29) >
    <|unfolded-io>
      let(cos(q[2]),c[2])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o29>)
      >>cos <around*|(|q<rsub|2>|)>\<longrightarrow\>c<rsub|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>30) >
    <|unfolded-io>
      let(sin(q[2]),s[2])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o30>)
      >>sin <around*|(|q<rsub|2>|)>\<longrightarrow\>s<rsub|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>31) >
    <|unfolded-io>
      eqq6:factor(letsimp(expand(eqq2)))
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o31>)
      >><around*|(|z<rsub|h>-D<rsub|1>|)><rsup|2>=<around*|(|s<rsub|2>*s<rsub|3>*D<rsub|4>-c<rsub|2>*c<rsub|3>*D<rsub|4>+A<rsub|2>*s<rsub|2>|)><rsup|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>32) >
    <|unfolded-io>
      eqq5:trigexpand(eqq1)
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o32>)
      >>y<rsub|h><rsup|2>+x<rsub|h><rsup|2>=<around*|(|D<rsub|4>*cos
      <around*|(|q<rsub|2>|)>*sin <around*|(|q<rsub|3>|)>+D<rsub|4>*sin
      <around*|(|q<rsub|2>|)>*cos <around*|(|q<rsub|3>|)>+A<rsub|2>*cos
      <around*|(|q<rsub|2>|)>|)><rsup|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>33) >
    <|unfolded-io>
      eqq7:factor(letsimp(expand(eqq5)))
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o33>)
      >>y<rsub|h><rsup|2>+x<rsub|h><rsup|2>=<around*|(|c<rsub|2>*s<rsub|3>*D<rsub|4>+s<rsub|2>*c<rsub|3>*D<rsub|4>+A<rsub|2>*c<rsub|2>|)><rsup|2>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>34) >
    <|unfolded-io>
      id2:c[2]^2+s[2]^2=1
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o34>)
      >>s<rsub|2><rsup|2>+c<rsub|2><rsup|2>=1>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>35) >
    <|unfolded-io>
      id3:c[3]^2+s[3]^2=1
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o35>)
      >>s<rsub|3><rsup|2>+c<rsub|3><rsup|2>=1>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>44) >
    <|unfolded-io>
      v:matrix([0],[A[2]])+rot2(q[3]).matrix([D[4]],[0])
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o44>)
      >><matrix|<tformat|<table|<row|<cell|D<rsub|4>*cos
      <around*|(|q<rsub|3>|)>>>|<row|<cell|D<rsub|4>*sin
      <around*|(|q<rsub|3>|)>+<around*|(|<sqrt|y<rsub|h><rsup|2>+x<rsub|h><rsup|2>>|)><rsub|2>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>37) >
    <|unfolded-io>
      C:v[1,1]
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o37>)
      >>A<rsub|2>-D<rsub|4>*sin <around*|(|q<rsub|3>|)>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>38) >
    <|unfolded-io>
      G:v[2,1]
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o38>)
      >>D<rsub|4>*cos <around*|(|q<rsub|3>|)>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>39) >
    <|unfolded-io>
      A:(sqrt(x[h]^2+y[h]^2))
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o39>)
      >><sqrt|y<rsub|h><rsup|2>+x<rsub|h><rsup|2>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>40) >
    <|unfolded-io>
      B:z[h]-D[1]
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o40>)
      >>z<rsub|h>-D<rsub|1>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>41) >
    <|unfolded-io>
      v:matrix([C,G],[-G,C]).matrix([A],[B])
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o41>)
      >><matrix|<tformat|<table|<row|<cell|D<rsub|4>*cos
      <around*|(|q<rsub|3>|)>*<around*|(|z<rsub|h>-D<rsub|1>|)>+<around*|(|A<rsub|2>-D<rsub|4>*sin
      <around*|(|q<rsub|3>|)>|)>*<sqrt|y<rsub|h><rsup|2>+x<rsub|h><rsup|2>>>>|<row|<cell|<around*|(|A<rsub|2>-D<rsub|4>*sin
      <around*|(|q<rsub|3>|)>|)>*<around*|(|z<rsub|h>-D<rsub|1>|)>-D<rsub|4>*cos
      <around*|(|q<rsub|3>|)>*<sqrt|y<rsub|h><rsup|2>+x<rsub|h><rsup|2>>>>>>>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>42) >
    <|unfolded-io>
      collectterms(eq1,c[1])
    <|unfolded-io>
      <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o42>)
      >>x<rsub|h>=D<rsub|4>*cos <around*|(|q<rsub|1>|)>*sin
      <around*|(|q<rsub|3>+q<rsub|2>|)>+A<rsub|2>*cos
      <around*|(|q<rsub|1>|)>*cos <around*|(|q<rsub|2>|)>>>
    </unfolded-io>

    <\unfolded-io>
      <with|color|red|(<with|math-font-family|rm|%i>43) >
    <|unfolded-io>
      A[2]*c[2]-D[4]*s[23]
    <|unfolded-io>
      \;

      \ <math|<with|math-display|true|<text|<with|font-family|tt|color|red|(<with|math-font-family|rm|%o43>)
      >>c<rsub|2>*<around*|(|<sqrt|y<rsub|h><rsup|2>+x<rsub|h><rsup|2>>|)><rsub|2>-D<rsub|4>*s<rsub|23>>>
    </unfolded-io>

    <\input>
      <with|color|red|(<with|math-font-family|rm|%i>44) >
    <|input>
      \;
    </input>
  </session>
</body>

<\initial>
  <\collection>
    <associate|page-height|auto>
    <associate|page-medium|paper>
    <associate|page-type|a3>
    <associate|page-width|auto>
  </collection>
</initial>