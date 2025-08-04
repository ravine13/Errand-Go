import { Button, Card, CardBody } from "@nextui-org/react"
import { Clock, Shield, Star, Zap } from "lucide-react"
import Link from "next/link"
import NavigationBar from "@/components/Navbar"

export default function HomePage() {
  const features = [
    {
      icon: <Zap className="w-8 h-8 text-primary" />,
      title: "Quick & Easy",
      description: "Post your task and get matched with qualified errand boys in minutes",
    },
    {
      icon: <Shield className="w-8 h-8 text-primary" />,
      title: "Trusted & Secure",
      description: "All errand boys are verified and insured for your peace of mind",
    },
    {
      icon: <Clock className="w-8 h-8 text-primary" />,
      title: "24/7 Available",
      description: "Get help whenever you need it, day or night",
    },
    {
      icon: <Star className="w-8 h-8 text-primary" />,
      title: "Quality Guaranteed",
      description: "Rate and review every service to maintain high standards",
    },
  ]

  const categories = [
    { name: "Shopping", icon: "🛒", description: "Grocery runs, retail shopping" },
    { name: "Cleaning", icon: "🧹", description: "House cleaning, organization" },
    { name: "Delivery", icon: "📦", description: "Package pickup and delivery" },
    { name: "Handyman", icon: "🔧", description: "Minor repairs and fixes" },
    { name: "Pet Care", icon: "🐕", description: "Dog walking, pet sitting" },
    { name: "Moving", icon: "📦", description: "Packing, moving assistance" },
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
      <NavigationBar />

      {/* Hero Section */}
      <section className="relative py-20 px-4 hero-pattern">
        <div className="max-w-6xl mx-auto text-center">
          <h1 className="text-5xl md:text-7xl font-bold text-gray-900 mb-6">
            Your Personal
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600">
              {" "}
              Task Assistant
            </span>
          </h1>
          <p className="text-xl md:text-2xl text-gray-600 mb-8 max-w-3xl mx-auto">
            Get your errands done by trusted professionals in your area. From shopping to cleaning, we've got you
            covered.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button as={Link} href="/register" size="lg" color="primary" className="text-lg px-8 py-6">
              Get Started
            </Button>
            <Button as={Link} href="/become-errand-boy" size="lg" variant="bordered" className="text-lg px-8 py-6">
              Become an Errand Boy
            </Button>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-20 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Why Choose Errand GO?</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
            {features.map((feature, index) => (
              <Card key={index} className="text-center p-6 hover:shadow-lg transition-shadow">
                <CardBody className="items-center">
                  <div className="mb-4">{feature.icon}</div>
                  <h3 className="text-xl font-semibold mb-2">{feature.title}</h3>
                  <p className="text-gray-600">{feature.description}</p>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Categories Section */}
      <section className="py-20 px-4 bg-gray-50">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Popular Categories</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            {categories.map((category, index) => (
              <Card key={index} className="hover:shadow-lg transition-shadow cursor-pointer">
                <CardBody className="p-6">
                  <div className="flex items-center gap-4">
                    <div className="text-4xl">{category.icon}</div>
                    <div>
                      <h3 className="text-xl font-semibold">{category.name}</h3>
                      <p className="text-gray-600">{category.description}</p>
                    </div>
                  </div>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* How It Works Section */}
      <section className="py-20 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">How It Works</h2>
          <div className="grid md:grid-cols-3 gap-8">
            <div className="text-center">
              <div className="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-white font-bold text-xl">1</span>
              </div>
              <h3 className="text-xl font-semibold mb-2">Post Your Task</h3>
              <p className="text-gray-600">Describe what you need done and set your budget</p>
            </div>
            <div className="text-center">
              <div className="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-white font-bold text-xl">2</span>
              </div>
              <h3 className="text-xl font-semibold mb-2">Get Matched</h3>
              <p className="text-gray-600">Qualified errand boys will apply for your task</p>
            </div>
            <div className="text-center">
              <div className="w-16 h-16 bg-primary rounded-full flex items-center justify-center mx-auto mb-4">
                <span className="text-white font-bold text-xl">3</span>
              </div>
              <h3 className="text-xl font-semibold mb-2">Task Complete</h3>
              <p className="text-gray-600">Your errand boy completes the task and you pay securely</p>
            </div>
          </div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-20 px-4 gradient-bg text-white">
        <div className="max-w-6xl mx-auto">
          <div className="grid md:grid-cols-4 gap-8 text-center">
            <div>
              <div className="text-4xl font-bold mb-2">10K+</div>
              <div className="text-lg opacity-90">Tasks Completed</div>
            </div>
            <div>
              <div className="text-4xl font-bold mb-2">5K+</div>
              <div className="text-lg opacity-90">Happy Customers</div>
            </div>
            <div>
              <div className="text-4xl font-bold mb-2">1K+</div>
              <div className="text-lg opacity-90">Errand Boys</div>
            </div>
            <div>
              <div className="text-4xl font-bold mb-2">4.9</div>
              <div className="text-lg opacity-90">Average Rating</div>
            </div>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 px-4 bg-white">
        <div className="max-w-4xl mx-auto text-center">
          <h2 className="text-4xl font-bold text-gray-900 mb-6">Ready to Get Started?</h2>
          <p className="text-xl text-gray-600 mb-8">
            Join thousands of satisfied customers who trust Errand GO with their tasks
          </p>
          <Button as={Link} href="/register" size="lg" color="primary" className="text-lg px-12 py-6">
            Start Your First Task
          </Button>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-gray-900 text-white py-12 px-4">
        <div className="max-w-6xl mx-auto">
          <div className="grid md:grid-cols-4 gap-8">
            <div>
              <h3 className="text-2xl font-bold mb-4">Errand GO</h3>
              <p className="text-gray-400">Your trusted partner for getting things done.</p>
            </div>
            <div>
              <h4 className="font-semibold mb-4">For Customers</h4>
              <ul className="space-y-2 text-gray-400">
                <li>
                  <Link href="/how-it-works">How it Works</Link>
                </li>
                <li>
                  <Link href="/categories">Browse Categories</Link>
                </li>
                <li>
                  <Link href="/safety">Safety</Link>
                </li>
              </ul>
            </div>
            <div>
              <h4 className="font-semibold mb-4">For Errand Boys</h4>
              <ul className="space-y-2 text-gray-400">
                <li>
                  <Link href="/become-errand-boy">Join Us</Link>
                </li>
                <li>
                  <Link href="/earnings">Earnings</Link>
                </li>
                <li>
                  <Link href="/resources">Resources</Link>
                </li>
              </ul>
            </div>
            <div>
              <h4 className="font-semibold mb-4">Support</h4>
              <ul className="space-y-2 text-gray-400">
                <li>
                  <Link href="/help">Help Center</Link>
                </li>
                <li>
                  <Link href="/contact">Contact Us</Link>
                </li>
                <li>
                  <Link href="/terms">Terms of Service</Link>
                </li>
              </ul>
            </div>
          </div>
          <div className="border-t border-gray-800 mt-8 pt-8 text-center text-gray-400">
            <p>&copy; 2024 Errand GO. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  )
}
