import { Card, CardBody, Button } from "@nextui-org/react"
import { CheckCircle, Clock, DollarSign, Star, Users, Shield, Zap } from "lucide-react"
import Link from "next/link"
import NavigationBar from "@/components/Navbar"

export default function HowItWorksPage() {
  const steps = [
    {
      number: "01",
      title: "Post Your Task",
      description: "Describe what you need done, set your budget, and specify when you need it completed.",
      details: [
        "Choose from popular categories or create a custom task",
        "Set a fair budget based on task complexity",
        "Add specific requirements and preferences",
        "Upload photos if needed for clarity",
      ],
      icon: <Zap className="w-8 h-8 text-primary" />,
    },
    {
      number: "02",
      title: "Get Matched",
      description: "Qualified errand boys in your area will review and apply for your task.",
      details: [
        "Receive applications from verified errand boys",
        "Review profiles, ratings, and past work",
        "Chat with applicants to discuss details",
        "Choose the best person for your task",
      ],
      icon: <Users className="w-8 h-8 text-primary" />,
    },
    {
      number: "03",
      title: "Task Completion",
      description: "Your chosen errand boy completes the task while you track progress.",
      details: [
        "Real-time updates on task progress",
        "Direct communication throughout",
        "Photo proof of completion when applicable",
        "Quality assurance and support",
      ],
      icon: <CheckCircle className="w-8 h-8 text-primary" />,
    },
    {
      number: "04",
      title: "Pay & Review",
      description: "Pay securely through our platform and leave a review for future users.",
      details: [
        "Secure payment processing",
        "Money held safely until completion",
        "Rate and review your experience",
        "Build trust in our community",
      ],
      icon: <Star className="w-8 h-8 text-primary" />,
    },
  ]

  const benefits = [
    {
      icon: <Clock className="w-6 h-6 text-blue-600" />,
      title: "Save Time",
      description: "Focus on what matters while we handle your errands",
    },
    {
      icon: <Shield className="w-6 h-6 text-green-600" />,
      title: "Stay Safe",
      description: "All errand boys are background checked and insured",
    },
    {
      icon: <DollarSign className="w-6 h-6 text-purple-600" />,
      title: "Fair Pricing",
      description: "Set your own budget and get competitive offers",
    },
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
      <NavigationBar />

      {/* Hero Section */}
      <section className="py-20 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <h1 className="text-5xl md:text-6xl font-bold text-gray-900 mb-6">
            How Errand GO
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600"> Works</span>
          </h1>
          <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
            Getting help with your tasks has never been easier. Here's how our platform connects you with trusted
            professionals in just a few simple steps.
          </p>
        </div>
      </section>

      {/* Steps Section */}
      <section className="py-16 px-4">
        <div className="max-w-6xl mx-auto">
          <div className="space-y-16">
            {steps.map((step, index) => (
              <div
                key={index}
                className={`flex flex-col lg:flex-row items-center gap-12 ${
                  index % 2 === 1 ? "lg:flex-row-reverse" : ""
                }`}
              >
                <div className="lg:w-1/2">
                  <Card className="p-8 hover:shadow-xl transition-shadow">
                    <CardBody>
                      <div className="flex items-center gap-4 mb-6">
                        <div className="w-16 h-16 bg-primary rounded-full flex items-center justify-center">
                          <span className="text-white font-bold text-xl">{step.number}</span>
                        </div>
                        <div>
                          <h3 className="text-2xl font-bold text-gray-900">{step.title}</h3>
                        </div>
                      </div>
                      <p className="text-gray-600 text-lg mb-6">{step.description}</p>
                      <ul className="space-y-3">
                        {step.details.map((detail, detailIndex) => (
                          <li key={detailIndex} className="flex items-start gap-3">
                            <CheckCircle className="w-5 h-5 text-green-500 mt-0.5 flex-shrink-0" />
                            <span className="text-gray-700">{detail}</span>
                          </li>
                        ))}
                      </ul>
                    </CardBody>
                  </Card>
                </div>
                <div className="lg:w-1/2 flex justify-center">
                  <div className="w-64 h-64 bg-gradient-to-br from-blue-100 to-purple-100 rounded-full flex items-center justify-center">
                    {step.icon}
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Benefits Section */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Why Choose Errand GO?</h2>
          <div className="grid md:grid-cols-3 gap-8">
            {benefits.map((benefit, index) => (
              <Card key={index} className="text-center p-6 hover:shadow-lg transition-shadow">
                <CardBody>
                  <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    {benefit.icon}
                  </div>
                  <h3 className="text-xl font-semibold mb-2">{benefit.title}</h3>
                  <p className="text-gray-600">{benefit.description}</p>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 px-4 gradient-bg text-white">
        <div className="max-w-4xl mx-auto text-center">
          <h2 className="text-4xl font-bold mb-6">Ready to Get Started?</h2>
          <p className="text-xl mb-8 opacity-90">
            Join thousands of satisfied customers who trust Errand GO with their tasks
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button as={Link} href="/register" size="lg" color="default" className="text-lg px-8 py-6">
              Post Your First Task
            </Button>
            <Button
              as={Link}
              href="/become-errand-boy"
              size="lg"
              variant="bordered"
              className="text-lg px-8 py-6 border-white text-white hover:bg-white hover:text-primary"
            >
              Become an Errand Boy
            </Button>
          </div>
        </div>
      </section>
    </div>
  )
}
