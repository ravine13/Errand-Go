"use client"

import { useState } from "react"
import { Card, CardBody, Input, Button, Accordion, AccordionItem } from "@nextui-org/react"
import { Search, MessageCircle, Phone, Mail, HelpCircle, Book, Users, Settings } from "lucide-react"
import NavigationBar from "@/components/Navbar"

export default function HelpPage() {
  const [searchQuery, setSearchQuery] = useState("")

  const helpCategories = [
    {
      icon: <Book className="w-8 h-8 text-blue-600" />,
      title: "Getting Started",
      description: "New to Errand GO? Learn the basics",
      articleCount: 12,
    },
    {
      icon: <Users className="w-8 h-8 text-green-600" />,
      title: "Account Management",
      description: "Manage your profile and settings",
      articleCount: 8,
    },
    {
      icon: <MessageCircle className="w-8 h-8 text-purple-600" />,
      title: "Tasks & Payments",
      description: "Everything about tasks and billing",
      articleCount: 15,
    },
    {
      icon: <Settings className="w-8 h-8 text-orange-600" />,
      title: "Technical Support",
      description: "App issues and troubleshooting",
      articleCount: 10,
    },
  ]

  const faqs = [
    {
      question: "How do I create my first task?",
      answer:
        "To create your first task, click on 'Post New Task' from your dashboard. Fill in the task details including title, description, category, location, and budget. Once submitted, errand boys in your area will be able to see and apply for your task.",
    },
    {
      question: "How are errand boys verified?",
      answer:
        "All errand boys go through a comprehensive verification process including background checks, identity verification with government-issued ID, and phone number confirmation. We also check references and previous work history where applicable.",
    },
    {
      question: "What payment methods do you accept?",
      answer:
        "We accept all major credit cards (Visa, MasterCard, American Express), debit cards, and PayPal. Payments are processed securely through our platform and funds are held until the task is completed to your satisfaction.",
    },
    {
      question: "What if I'm not satisfied with the work?",
      answer:
        "If you're not satisfied with the completed task, you can request revisions or dispute the payment through our resolution center. Our support team will review the case and work with both parties to reach a fair solution.",
    },
    {
      question: "How do I become an errand boy?",
      answer:
        "To become an errand boy, sign up with an @errandboy.com email address, complete your profile with relevant skills and experience, upload required documents for verification, and pass our background check process. Once approved, you can start applying for tasks.",
    },
    {
      question: "What are the fees for using Errand GO?",
      answer:
        "For customers, posting tasks is free. We charge a small service fee (typically 3-5%) on completed transactions. For errand boys, we take a commission from completed tasks (typically 15-20%) which covers platform maintenance, insurance, and support services.",
    },
    {
      question: "How do I cancel a task?",
      answer:
        "You can cancel a task from your dashboard if no errand boy has been assigned yet. If an errand boy has already been assigned, you'll need to contact them directly or reach out to our support team. Cancellation policies may apply depending on the timing.",
    },
    {
      question: "Is there insurance coverage for tasks?",
      answer:
        "Yes, all tasks completed through Errand GO are covered by our comprehensive insurance policy. This includes liability coverage for errand boys and protection for customers' property during task completion.",
    },
  ]

  const contactOptions = [
    {
      icon: <MessageCircle className="w-6 h-6 text-blue-600" />,
      title: "Live Chat",
      description: "Chat with our support team",
      availability: "Available 24/7",
      action: "Start Chat",
    },
    {
      icon: <Phone className="w-6 h-6 text-green-600" />,
      title: "Phone Support",
      description: "Speak directly with an agent",
      availability: "Mon-Fri 8AM-8PM EST",
      action: "Call Now",
    },
    {
      icon: <Mail className="w-6 h-6 text-purple-600" />,
      title: "Email Support",
      description: "Send us a detailed message",
      availability: "Response within 24 hours",
      action: "Send Email",
    },
  ]

  const filteredFAQs = faqs.filter(
    (faq) =>
      faq.question.toLowerCase().includes(searchQuery.toLowerCase()) ||
      faq.answer.toLowerCase().includes(searchQuery.toLowerCase()),
  )

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-purple-50">
      <NavigationBar />

      {/* Hero Section */}
      <section className="py-20 px-4">
        <div className="max-w-4xl mx-auto text-center">
          <div className="w-20 h-20 bg-blue-100 rounded-full flex items-center justify-center mx-auto mb-6">
            <HelpCircle className="w-10 h-10 text-blue-600" />
          </div>
          <h1 className="text-5xl md:text-6xl font-bold text-gray-900 mb-6">
            Help
            <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-600 to-purple-600"> Center</span>
          </h1>
          <p className="text-xl text-gray-600 mb-8 max-w-2xl mx-auto">
            Find answers to your questions and get the support you need
          </p>

          {/* Search Bar */}
          <div className="max-w-2xl mx-auto">
            <Input
              size="lg"
              placeholder="Search for help articles, FAQs, or topics..."
              value={searchQuery}
              onChange={(e) => setSearchQuery(e.target.value)}
              startContent={<Search className="w-5 h-5 text-gray-400" />}
              className="mb-8"
            />
          </div>
        </div>
      </section>

      {/* Help Categories */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Browse by Category</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-6">
            {helpCategories.map((category, index) => (
              <Card key={index} className="text-center p-6 hover:shadow-lg transition-shadow cursor-pointer">
                <CardBody>
                  <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    {category.icon}
                  </div>
                  <h3 className="text-xl font-semibold mb-2">{category.title}</h3>
                  <p className="text-gray-600 mb-3">{category.description}</p>
                  <p className="text-sm text-primary font-medium">{category.articleCount} articles</p>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* FAQs */}
      <section className="py-16 px-4">
        <div className="max-w-4xl mx-auto">
          <h2 className="text-4xl font-bold text-center text-gray-900 mb-16">Frequently Asked Questions</h2>
          <Card>
            <CardBody>
              <Accordion variant="splitted">
                {filteredFAQs.map((faq, index) => (
                  <AccordionItem key={index} aria-label={faq.question} title={faq.question} className="text-left">
                    <p className="text-gray-600 leading-relaxed">{faq.answer}</p>
                  </AccordionItem>
                ))}
              </Accordion>
            </CardBody>
          </Card>
        </div>
      </section>

      {/* Contact Support */}
      <section className="py-16 px-4 bg-white">
        <div className="max-w-6xl mx-auto">
          <div className="text-center mb-16">
            <h2 className="text-4xl font-bold text-gray-900 mb-4">Still Need Help?</h2>
            <p className="text-xl text-gray-600">Our support team is here to help you with any questions or issues</p>
          </div>
          <div className="grid md:grid-cols-3 gap-8">
            {contactOptions.map((option, index) => (
              <Card key={index} className="text-center p-6 hover:shadow-lg transition-shadow">
                <CardBody>
                  <div className="w-16 h-16 bg-gray-100 rounded-full flex items-center justify-center mx-auto mb-4">
                    {option.icon}
                  </div>
                  <h3 className="text-xl font-semibold mb-2">{option.title}</h3>
                  <p className="text-gray-600 mb-2">{option.description}</p>
                  <p className="text-sm text-gray-500 mb-4">{option.availability}</p>
                  <Button color="primary" variant="flat">
                    {option.action}
                  </Button>
                </CardBody>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Quick Links */}
      <section className="py-16 px-4 bg-gray-50">
        <div className="max-w-4xl mx-auto text-center">
          <h2 className="text-3xl font-bold text-gray-900 mb-8">Quick Links</h2>
          <div className="grid md:grid-cols-2 lg:grid-cols-4 gap-4">
            <Button variant="flat" className="h-auto p-4 flex-col">
              <span className="font-semibold">Safety Guidelines</span>
              <span className="text-sm text-gray-600">Learn about our safety measures</span>
            </Button>
            <Button variant="flat" className="h-auto p-4 flex-col">
              <span className="font-semibold">Payment Issues</span>
              <span className="text-sm text-gray-600">Resolve billing problems</span>
            </Button>
            <Button variant="flat" className="h-auto p-4 flex-col">
              <span className="font-semibold">Account Settings</span>
              <span className="text-sm text-gray-600">Manage your profile</span>
            </Button>
            <Button variant="flat" className="h-auto p-4 flex-col">
              <span className="font-semibold">Report an Issue</span>
              <span className="text-sm text-gray-600">Submit a support ticket</span>
            </Button>
          </div>
        </div>
      </section>
    </div>
  )
}
