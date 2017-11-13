#include <math.h>
#include <vector>
#include <iostream>

typedef float degree;
constexpr float PI = 3.14159265358979323846; /* pi */

struct simple_vector
{
    float x;
    float y;
};

simple_vector multiply_simple_vector(simple_vector p_left_hand, float p_scalar)
{
    simple_vector new_vector = p_left_hand;
    p_left_hand.x += p_left_hand.x * p_scalar;
    p_left_hand.y += p_left_hand.y * p_scalar;

    return new_vector;
}

simple_vector rotate_simple_vector(simple_vector p_input_vec, degree p_rotation)
{
    simple_vector new_vector;
    new_vector.x = p_input_vec.x * cos(p_rotation) - p_input_vec.y * sin(p_rotation);
    new_vector.y = p_input_vec.x * sin(p_rotation) + p_input_vec.y * cos(p_rotation);

    return new_vector;
}

float to_radian(float p_degree)
{
    return PI * 2 * (p_degree / 360);
}

std::vector<simple_vector> generate_star_points(int p_num_stars, degree p_outer_r, degree p_inner_r)
{
    std::vector<simple_vector> star_points;
    simple_vector direction = {0,1};
    degree angle = 360.f/p_num_stars;
    std::cout << angle << std::endl;
    float radius;

    for(int i = 0; i < p_num_stars; i++)
    {
        if(i % 2 == 0)
        {
           radius = p_outer_r;
        }
        else
        {
            radius = p_inner_r;
        }
        star_points.push_back(multiply_simple_vector(rotate_simple_vector(direction, to_radian(angle) * i), radius));
    }
    return star_points;
}


int main(int argc, char** argv)
{
    int num_points;
    degree outer_r = 2;
    degree inner_r = 1;

    int number_of_points = 10;

    std::vector<simple_vector> star_points = generate_star_points(number_of_points, outer_r, inner_r);
    for(int i = 0; i < number_of_points; i ++)
    {
        std::cout << "["<<star_points[i].x<<","<< star_points[i].y<<"] "<< std::endl;
    }

}
