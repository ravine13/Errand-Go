"use client"

import type React from "react"

import { useState } from "react"
import { Card, CardBody, CardHeader, Input, Textarea, Button, Select, SelectItem } from "@nextui-org/react"
import { Mail, Phone, MapPin, Clock, Send, MessageCircle } from "lucide-react"
import NavigationBar from "@/components/Navbar"

export default function ContactPage() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    subject: "",
    category: "",
    message: "",
  })
  const [isLoading, setIsLoading] = useState(false)

  const contactInfo = [
    {
      icon: <Mail className="w-6 h-6 text-blue-600" />,
      title: "Email Us",
      details: "support@errandgo.com",
      description: "Send us an email and we'll respond within 24 hours",
    },
    {
      icon: <Phone className="w-6 h-6 text-green-600" />,
      title: "Call Us",
      details: "+254 725 123456",
      description: "Mon-Fri 8AM-8PM EST, Sat-Sun 10AM-6PM EST",
    },
    {
      icon: <MapPin className="w-6 h-6 text-purple-600" />,
      title: "Visit Us",
      details: "Global Trade Center, Suite 133",
      description: "Westlands, Nairobi, KE",
    },
    {
      icon: <Clock className="w-6 h-6 text-orange-600" />,
      title: "Business Hours",
      details: "Monday - Friday: 8AM - 8PM EST",
      description: "Weekend: 10AM - 6PM EST",
    },
  ]

  const categories = [
    { key: "general", label: "General Inquiry" },
    { key: "technical", label: "Technical Support" },
    { key: "billing", label: "Billing & Payments" },
    { key: "safety", label: "Safety Concerns" },
    { key: "partnership", label: "Partnership Opportunities" },
    { key: "press", label: "Press & Media" },
  ]

  const handleInputChange = (field: string, value: string) => {
    setFormData((prev) => ({ ...prev, [field]: value }))
  }

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setIsLoading(true)

    // Simulate form submission
    await new Promise((resolve) => setTimeout(resolve, 2000))

    // Reset form
    setFormData({
      name: "",
      email: "",
      subject: "",
      category: "",
      message: "",
    })

    setIsLoading(false)
    alert("Thank you for your message! We'll get back to you soon.")
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
      <NavigationBar />

      {/* Hero Section */}
      <section className="py-20 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <div className="w-20 h-20 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6">
            <MessageCircle className="w-10 h-10 text-blue-600" />
          </div>
          <h1 className="text-5xl md:text-6xl font-bold text-gray-900 mb-6">
            Contact
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600"> Us</span>
          </h1>
          <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
            Have a question or need support? We're here to help. Reach out to us through any of the channels below.
          </p>
        </div>
      </section>

      {/* Contact Info Cards */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6 mb-16">
            {contactInfo.map((info, index) => (
              <Card key={index} className="text-center p-6 hover:shadow-lg transition-shadow">
                <CardBody>
                  <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    {info.icon}
                  </div>
                  <h3 className="text-lg font-semibold mb-2">{info.title}</h3>
                  <p className="text-primary font-medium mb-2">{info.details}</p>
                  <p className="text-gray-600 text-sm">{info.description}</p>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Contact Form */}
      <section className="py-16 px-4">
        <div className="max-w-4xl mx-auto">
          <Card>
            <CardHeader>
              <div className="text-center w-full">
                <h2 className="text-3xl font-bold text-gray-900 mb-2">Send Us a Message</h2>
                <p className="text-gray-600">Fill out the form below and we'll get back to you as soon as possible</p>
              </div>
            </CardHeader>
            <CardBody className="p-8">
              <form onSubmit={handleSubmit} className="space-y-6">
                <div className="grid md:grid-cols-2 gap-6">
                  <Input
                    label="Full Name"
                    placeholder="Enter your full name"
                    value={formData.name}
                    onChange={(e) => handleInputChange("name", e.target.value)}
                    isRequired
                  />
                  <Input
                    type="email"
                    label="Email Address"
                    placeholder="Enter your email"
                    value={formData.email}
                    onChange={(e) => handleInputChange("email", e.target.value)}
                    isRequired
                  />
                </div>

                <div className="grid md:grid-cols-2 gap-6">
                  <Select
                    label="Category"
                    placeholder="Select a category"
                    value={formData.category}
                    onChange={(e) => handleInputChange("category", e.target.value)}
                    isRequired
                  >
                    {categories.map((category) => (
                      <SelectItem key={category.key} value={category.key}>
                        {category.label}
                      </SelectItem>
                    ))}
                  </Select>
                  <Input
                    label="Subject"
                    placeholder="Brief description of your inquiry"
                    value={formData.subject}
                    onChange={(e) => handleInputChange("subject", e.target.value)}
                    isRequired
                  />
                </div>

                <Textarea
                  label="Message"
                  placeholder="Please provide details about your inquiry..."
                  value={formData.message}
                  onChange={(e) => handleInputChange("message", e.target.value)}
                  minRows={6}
                  isRequired
                />

                <Button
                  type="submit"
                  color="primary"
                  size="lg"
                  className="w-full"
                  isLoading={isLoading}
                  startContent={<Send className="w-4 h-4" />}
                >
                  Send Message
                </Button>
              </form>
            </CardBody>
          </Card>
        </div>
      </section>

      {/* FAQ Section */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-4xl mx-auto text-center">
          <h2 className="text-3xl font-bold text-gray-900 mb-6">Before You Contact Us</h2>
          <p className="text-gray-600 mb-8">
            Check out our frequently asked questions - you might find your answer there!
          </p>
          <div className="grid md:grid-cols-3 gap-6">
            <Card className="p-6 hover:shadow-lg transition-shadow">
              <CardBody className="text-center">
                <h3 className="font-semibold mb-2">Account Issues</h3>
                <p className="text-gray-600 text-sm mb-4">Problems with login, profile, or settings</p>
                <Button variant="flat" size="sm">
                  View FAQs
                </Button>
              </CardBody>
            </Card>
            <Card className="p-6 hover:shadow-lg transition-shadow">
              <CardBody className="text-center">
                <h3 className="font-semibold mb-2">Payment Help</h3>
                <p className="text-gray-600 text-sm mb-4">Billing questions and payment issues</p>
                <Button variant="flat" size="sm">
                  View FAQs
                </Button>
              </CardBody>
            </Card>
            <Card className="p-6 hover:shadow-lg transition-shadow">
              <CardBody className="text-center">
                <h3 className="font-semibold mb-2">Getting Started</h3>
                <p className="text-gray-600 text-sm mb-4">New user guides and tutorials</p>
                <Button variant="flat" size="sm">
                  View FAQs
                </Button>
              </CardBody>
            </Card>
          </div>
        </div>
      </section>

      {/* Emergency Contact */}
      <section className="py-16 px-4 bg-red-50">
        <div className="max-w-4xl mx-auto text-center">
          <div className="w-20 h-20 bg-red-100 rounded-full flex items-center justify-center mx-auto mb-6">
            <Phone className="w-10 h-10 text-red-600" />
          </div>
          <h2 className="text-3xl font-bold text-gray-900 mb-4">Emergency Support</h2>
          <p className="text-lg text-gray-600 mb-6">For urgent safety concerns or emergencies during a task</p>
          <div className="bg-white rounded-lg p-8 shadow-lg max-w-md mx-auto">
            <div className="text-3xl font-bold text-red-600 mb-2">+254 725 123456</div>
            <p className="text-gray-600">Available 24/7 for safety emergencies</p>
          </div>
        </div>
      </section>
    </div>
  )
}
