import { Card, CardBody, CardHeader, Button } from "@nextui-org/react"
import { BookOpen, DollarSign, Star, TrendingUp, Download, Video, Users } from "lucide-react"
import NavigationBar from "@/components/Navbar"

export default function ResourcesPage() {
  const resourceCategories = [
    {
      icon: <BookOpen className="w-8 h-8 text-blue-600" />,
      title: "Getting Started Guide",
      description: "Everything you need to know to begin your journey as an errand boy",
      resources: [
        "Complete onboarding checklist",
        "Profile optimization tips",
        "First task best practices",
        "Platform navigation guide",
      ],
    },
    {
      icon: <DollarSign className="w-8 h-8 text-green-600" />,
      title: "Maximizing Earnings",
      description: "Strategies to increase your income and build a successful errand business",
      resources: [
        "Pricing strategies guide",
        "Peak hours optimization",
        "Upselling techniques",
        "Tax preparation tips",
      ],
    },
    {
      icon: <Star className="w-8 h-8 text-yellow-600" />,
      title: "Building Your Reputation",
      description: "Learn how to get great reviews and build trust with customers",
      resources: [
        "Customer service excellence",
        "Communication best practices",
        "Handling difficult situations",
        "Review management guide",
      ],
    },
    {
      icon: <TrendingUp className="w-8 h-8 text-purple-600" />,
      title: "Growing Your Business",
      description: "Advanced strategies for experienced errand boys looking to scale",
      resources: [
        "Specialization opportunities",
        "Building repeat customers",
        "Seasonal strategy planning",
        "Business expansion tips",
      ],
    },
  ]

  const tools = [
    {
      name: "Errand Boy Mobile App",
      description: "Stay connected and manage tasks on the go",
      type: "App",
      icon: <Download className="w-6 h-6" />,
    },
    {
      name: "Earnings Calculator",
      description: "Calculate potential earnings based on your availability",
      type: "Tool",
      icon: <DollarSign className="w-6 h-6" />,
    },
    {
      name: "Tax Document Generator",
      description: "Generate necessary tax documents for your earnings",
      type: "Tool",
      icon: <BookOpen className="w-6 h-6" />,
    },
    {
      name: "Performance Dashboard",
      description: "Track your ratings, earnings, and task completion stats",
      type: "Dashboard",
      icon: <TrendingUp className="w-6 h-6" />,
    },
  ]

  const trainingModules = [
    {
      title: "Customer Service Excellence",
      duration: "45 minutes",
      level: "Beginner",
      description: "Learn the fundamentals of providing exceptional customer service",
    },
    {
      title: "Safety Protocols",
      duration: "30 minutes",
      level: "Required",
      description: "Essential safety guidelines for all types of tasks",
    },
    {
      title: "Advanced Task Management",
      duration: "60 minutes",
      level: "Intermediate",
      description: "Efficiently handle multiple tasks and complex requirements",
    },
    {
      title: "Business Development",
      duration: "90 minutes",
      level: "Advanced",
      description: "Build your personal brand and grow your errand business",
    },
  ]

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
      <NavigationBar />

      {/* Hero Section */}
      <section className="py-20 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <div className="w-20 h-20 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6">
            <BookOpen className="w-10 h-10 text-blue-600" />
          </div>
          <h1 className="text-5xl md:text-6xl font-bold text-gray-900 mb-6">
            Errand Boy
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600">
              {" "}
              Resources
            </span>
          </h1>
          <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
            Everything you need to succeed as an errand boy. From getting started guides to advanced business
            strategies.
          </p>
        </div>
      </section>

      {/* Resource Categories */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Resource Categories</h2>
          <div className="grid md:grid-cols-2 gap-8">
            {resourceCategories.map((category, index) => (
              <Card key={index} className="hover:shadow-lg transition-shadow">
                <CardHeader>
                  <div className="flex items-center gap-4">
                    <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center">
                      {category.icon}
                    </div>
                    <div>
                      <h3 className="text-xl font-semibold">{category.title}</h3>
                      <p className="text-gray-600">{category.description}</p>
                    </div>
                  </div>
                </CardHeader>
                <CardBody>
                  <ul className="space-y-2">
                    {category.resources.map((resource, resourceIndex) => (
                      <li key={resourceIndex} className="flex items-center gap-2">
                        <div className="w-2 h-2 bg-primary rounded-full"></div>
                        <span className="text-gray-700">{resource}</span>
                      </li>
                    ))}
                  </ul>
                  <Button color="primary" variant="flat" className="mt-4">
                    Explore Resources
                  </Button>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Tools & Apps */}
      <section className="py-16 px-4">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Tools & Apps</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
            {tools.map((tool, index) => (
              <Card key={index} className="text-center p-6 hover:shadow-lg transition-shadow">
                <CardBody>
                  <div className="w-16 h-16 bg-primary/10 rounded-full flex items-center justify-center mx-auto mb-4">
                    {tool.icon}
                  </div>
                  <h3 className="text-lg font-semibold mb-2">{tool.name}</h3>
                  <p className="text-gray-600 text-sm mb-3">{tool.description}</p>
                  <div className="text-xs text-primary font-medium mb-3">{tool.type}</div>
                  <Button size="sm" color="primary" variant="flat">
                    Access Tool
                  </Button>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Training Modules */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">Training Modules</h2>
            <p className="text-xl text-gray-600">Enhance your skills with our comprehensive training programs</p>
          </div>
          <div className="grid md:grid-cols-2 gap-6">
            {trainingModules.map((module, index) => (
              <Card key={index} className="hover:shadow-lg transition-shadow">
                <CardBody className="p-6">
                  <div className="flex items-start justify-between mb-4">
                    <div>
                      <h3 className="text-xl font-semibold mb-2">{module.title}</h3>
                      <p className="text-gray-600">{module.description}</p>
                    </div>
                    <Video className="w-8 h-8 text-primary flex-shrink-0" />
                  </div>
                  <div className="flex items-center gap-4 mb-4">
                    <div className="flex items-center gap-1">
                      <span className="text-sm text-gray-500">Duration:</span>
                      <span className="text-sm font-medium">{module.duration}</span>
                    </div>
                    <div className="flex items-center gap-1">
                      <span className="text-sm text-gray-500">Level:</span>
                      <span
                        className={`text-sm font-medium ${
                          module.level === "Required"
                            ? "text-red-600"
                            : module.level === "Beginner"
                              ? "text-green-600"
                              : module.level === "Intermediate"
                                ? "text-yellow-600"
                                : "text-purple-600"
                        }`}
                      >
                        {module.level}
                      </span>
                    </div>
                  </div>
                  <Button color="primary" variant="flat" className="w-full">
                    Start Module
                  </Button>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Community Section */}
      <section className="py-16 px-4 gradient-bg text-white">
        <div className="max-w-4xl mx-auto text-center">
          <div className="w-20 h-20 bg-white/20 rounded-full flex items-center justify-center mx-auto mb-6">
            <Users className="w-10 h-10 text-white" />
          </div>
          <h2 className="text-4xl font-bold mb-6">Join Our Community</h2>
          <p className="text-xl mb-8 opacity-90">
            Connect with other errand boys, share tips, and learn from experienced professionals
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Button size="lg" color="default" className="text-lg px-8 py-6">
              Join Community Forum
            </Button>
            <Button
              size="lg"
              variant="bordered"
              className="text-lg px-8 py-6 border-white text-white hover:bg-white hover:text-primary"
            >
              Follow on Social Media
            </Button>
          </div>
        </div>
      </section>
    </div>
  )
}
