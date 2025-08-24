import { Card, CardBody, CardHeader } from "@nextui-org/react"
import { Shield, CheckCircle, AlertTriangle, Phone, Lock, Users } from "lucide-react"
import NavigationBar from "@/components/Navbar"

export default function SafetyPage() {
  const safetyFeatures = [
    {
      icon: <Shield className="w-8 h-8 text-blue-600" />,
      title: "Background Checks",
      description: "All errand boys undergo comprehensive background verification before joining our platform.",
    },
    {
      icon: <Lock className="w-8 h-8 text-green-600" />,
      title: "Secure Payments",
      description: "Your money is held securely until the task is completed to your satisfaction.",
    },
    {
      icon: <Users className="w-8 h-8 text-purple-600" />,
      title: "Identity Verification",
      description: "Every user must verify their identity with government-issued ID and phone number.",
    },
    {
      icon: <Phone className="w-8 h-8 text-red-600" />,
      title: "24/7 Support",
      description: "Our safety team is available around the clock to handle any concerns or emergencies.",
    },
  ]

  const safetyTips = [
    {
      category: "For Customers",
      tips: [
        "Always communicate through the Errand GO platform",
        "Check errand boy profiles and reviews before hiring",
        "Be specific about task requirements and expectations",
        "Report any suspicious behavior immediately",
        "Don't share personal financial information",
        "Meet in public places when possible",
      ],
    },
    {
      category: "For Errand Boys",
      tips: [
        "Verify task details before starting work",
        "Take photos to document task completion",
        "Follow all safety protocols for the task type",
        "Don't accept cash payments outside the platform",
        "Report unsafe working conditions",
        "Trust your instincts - decline tasks that feel unsafe",
      ],
    },
  ]

  const emergencyProcedures = [
    {
      title: "During a Task",
      steps: [
        "Stop the task immediately if you feel unsafe",
        "Contact our emergency hotline: +254 725 123456",
        "If in immediate danger, call local emergency services",
        "Report the incident through the app",
      ],
    },
    {
      title: "After an Incident",
      steps: [
        "Document everything that happened",
        "Contact our safety team within 24 hours",
        "Cooperate with any investigation",
        "Seek medical attention if needed",
      ],
    },
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
      <NavigationBar />

      {/* Hero Section */}
      <section className="py-20 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <div className="w-20 h-20 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6">
            <Shield className="w-10 h-10 text-blue-600" />
          </div>
          <h1 className="text-5xl md:text-6xl font-bold text-gray-900 mb-6">
            Your Safety is Our
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600">
              {" "}
              Priority
            </span>
          </h1>
          <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
            We've built comprehensive safety measures to ensure every interaction on Errand GO is secure and
            trustworthy.
          </p>
        </div>
      </section>

      {/* Safety Features */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Our Safety Features</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-8">
            {safetyFeatures.map((feature, index) => (
              <Card key={index} className="text-center p-6 hover:shadow-lg transition-shadow">
                <CardBody>
                  <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    {feature.icon}
                  </div>
                  <h3 className="text-xl font-semibold mb-2">{feature.title}</h3>
                  <p className="text-gray-600">{feature.description}</p>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Safety Tips */}
      <section className="py-16 px-4">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Safety Guidelines</h2>
          <div className="grid lg:grid-cols-2 gap-8">
            {safetyTips.map((section, index) => (
              <Card key={index}>
                <CardHeader>
                  <h3 className="text-2xl font-semibold">{section.category}</h3>
                </CardHeader>
                <CardBody>
                  <ul className="space-y-3">
                    {section.tips.map((tip, tipIndex) => (
                      <li key={tipIndex} className="flex items-start gap-3">
                        <CheckCircle className="w-5 h-5 text-green-500 mt-0.5 flex-shrink-0" />
                        <span className="text-gray-700">{tip}</span>
                      </li>
                    ))}
                  </ul>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Emergency Procedures */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Emergency Procedures</h2>
          <div className="grid lg:grid-cols-2 gap-8">
            {emergencyProcedures.map((procedure, index) => (
              <Card key={index} className="border-l-4 border-red-500">
                <CardHeader className="bg-red-50">
                  <div className="flex items-center gap-3">
                    <AlertTriangle className="w-6 h-6 text-red-600" />
                    <h3 className="text-2xl font-semibold text-red-800">{procedure.title}</h3>
                  </div>
                </CardHeader>
                <CardBody>
                  <ol className="space-y-3">
                    {procedure.steps.map((step, stepIndex) => (
                      <li key={stepIndex} className="flex items-start gap-3">
                        <div className="w-6 h-6 bg-red-100 rounded-full flex items-center justify-center flex-shrink-0 mt-0.5">
                          <span className="text-red-600 font-semibold text-sm">{stepIndex + 1}</span>
                        </div>
                        <span className="text-gray-700">{step}</span>
                      </li>
                    ))}
                  </ol>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Emergency Contact */}
      <section className="py-16 px-4 bg-red-50">
        <div className="max-w-4xl mx-auto text-center">
          <div className="w-20 h-20 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-6">
            <Phone className="w-10 h-10 text-red-600" />
          </div>
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Emergency Contact</h2>
          <p className="text-lg text-gray-600 mb-6">
            If you're in immediate danger or need urgent assistance during a task
          </p>
          <div className="bg-white rounded-lg p-8 shadow-lg">
            <div className="text-4xl font-bold text-red-600 mb-2">+254 725 123456</div>
            <p className="text-gray-600">Available 24/7 for safety emergencies</p>
          </div>
        </div>
      </section>
    </div>
  )
}
