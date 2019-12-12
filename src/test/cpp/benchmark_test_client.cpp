#include <cstdlib>
#include <iostream>
#include <chrono>
#include <atomic>
#include <thread>
#include <string>
#include <vector>
#include <algorithm>

#include <tango.h>
#include <future>

using Shape = std::vector<int>;
using Value = std::vector<double>;

template <typename Time>
double timeDelta(const Time& t1, const Time& t2)
{
    std::chrono::duration<double> d = t2 - t1;
    return d.count();
}

const auto kNumberOfClients = 64;

int main(int, char**)
{
    std::atomic_long counter{ 0 };
    std::atomic_long errors{ 0 };
    std::atomic_bool finish{ false  };


    for(auto i =0; i<kNumberOfClients;++i)
    {
        std::thread{
            [&finish, &counter, &errors]()
            {
                Tango::DeviceAttribute attribute("BenchmarkScalarAttribute", 3.14);//double_scalar_w

                auto proxy = Tango::DeviceProxy("tango://hzgxenvtest:10000/development/benchmark/0");//sys/tg_test/0

                while (!finish)
                {
                    try
                    {
                        proxy.write_attribute(attribute);
                        counter++;
                    }
                    catch (...)
                    {
                        errors++;
                    }
                }
            }
        }.detach();
    }

    using namespace std::chrono_literals;
    std::this_thread::sleep_for(15s);

    finish = true;

    cout
        << "total writes:"
        << counter << "\n"
        <<  "total errors: "
        << errors << "\n";

    return 0;
}
