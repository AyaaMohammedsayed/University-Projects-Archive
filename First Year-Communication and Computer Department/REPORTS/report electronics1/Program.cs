using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;


namespace report_electronics1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            /* double tem, ni;
             Console.WriteLine("enter temp in kelven to calculate ni or enter  -1 to exist from program");
             Console.WriteLine("enter temp in kelven");
             tem =Convert.ToDouble(Console.ReadLine());
             double nc = 3.2E19, nd = 1E16, nv = 2E19, k = 1.38E-23, eg = 1.12;

             while (tem > 0)
             {
                 double r = (k * tem);
                 double g = r / (1.6E-19);
                 double y = -1 * eg;
                 double res = y / g;
                 ni = (Math.Sqrt(nc * nv)) * Math.Pow(2.718, res);
                 Console.WriteLine(" the value of ni is" + ni);
                 Console.WriteLine("enter temp in kelven");
                 tem = Convert.ToDouble(Console.ReadLine());

             }
             if (tem < 0)
             {
                 Console.WriteLine("enter positive temp in kelvin");
             }*/
           /* double tem, ni,t1=300;
            double nc, nd = 1E16,nv, nv0 = 1.8E16, na = 3.2E19, k = 1.38E-23, h = 6.63E-34, m0 = 9.1E-31, eg = 1.12;
            Console.WriteLine("enter temp in kelven to calculate ni or enter  -1 to exist from program");
            Console.WriteLine("enter temp in kelven");
            tem = Convert.ToDouble(Console.ReadLine());
            while (tem > 0)
            {
                nv = nv0*Math.Pow(tem / t1, 1.5);

              

                Console.WriteLine(" the value of nv is " + nv);
                double m = 1.08 * m0;
                double upper = 2 * 3.14 * m * k * tem;
                double down = h * h;
                double total = upper / down;
                nc = 2 * (Math.Pow(total, 1.5));
                Console.WriteLine("the value of nc is " + nc);

                double r = (k * tem);
                double g = r / (1.6E-19);
                double y = -1 * eg;
                double res = y / g;
                ni = (Math.Sqrt(nc * nv)) * Math.Pow(2.718, res);
                Console.WriteLine("the value of ni is " + ni);
                double p = na - nd;
                double n = (ni * ni) / p;
                Console.WriteLine("the value of n is " + n);
                Console.WriteLine("enter temp in kelven to calculate ni or enter  -1 to exist from program");
                Console.WriteLine("enter temp in kelven");
                tem = Convert.ToDouble(Console.ReadLine());


            }
            if (tem < 0)
            {
                Console.WriteLine("enter positive temp in kelvin");
            }*/










































































            Console.ReadLine();


        }
    }
}
